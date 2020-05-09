package com.lyuan.admin.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyuan.admin.course.entity.Course;
import com.lyuan.admin.course.mapper.CourseMapper;
import com.lyuan.admin.item.entity.Item;
import com.lyuan.admin.item.entity.ItemListDTO;
import com.lyuan.admin.item.mapper.ItemMapper;
import com.lyuan.admin.item.service.IItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.user_course.service.IUserCourseService;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.ListQueryUtil;
import com.lyuan.common.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Resource
    ItemMapper itemMapper;
    @Autowired
    private IUserCourseService iUserCourseService;
    @Resource
    private CourseMapper courseMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public BaseResponse list(ItemListDTO listDTO) {
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(listDTO.getName())){
            wrapper.like("name",listDTO.getName());
        }
        if (listDTO.getYear()!=null){
            wrapper.eq("year",listDTO.getYear());
        }
        if (!StringUtils.isEmpty(listDTO.getQuarter())){
            wrapper.eq("quarter",listDTO.getQuarter());
        }
        ListQueryUtil.orderHandler(wrapper,listDTO);
        Page page = new Page<>(listDTO.getCurrentPage(), listDTO.getPageSize());
        IPage iPage = itemMapper.selectPage(page, wrapper);
        return ResponseEnum.SUCCESS.responseList(iPage.getRecords(), iPage.getPages(), iPage.getTotal());
    }

    @Override
    public BaseResponse get(Integer id) {
        Item item = getById(id);
        if (item == null) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(item);
    }

    @Override
    public BaseResponse delete(Integer id) {
        if (!removeById(id)) {
            return ResponseEnum.FAIL.response();
        }
        redisUtil.itemRemove(id);
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse add(Item item) {
        if (item.getStartTime().after(item.getEndTime())){
            return ResponseEnum.ITEM_TIME_ERROR.response();
        }
        if (!save(item)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse update(Item item) {
        item.setEnable(false);
        if (!updateById(item)) {
            return ResponseEnum.FAIL.response();
        }
        redisUtil.itemRemove(item.getId());
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse enable(Integer id) {
        Item item = getById(id);
        if (item==null){
            return ResponseEnum.ITEM_NOT_EXIST.response();
        }
        if (!update(new UpdateWrapper<Item>().eq("id",id).set("enable",!item.getEnable()))){
            return ResponseEnum.ITEM_ENABLE_FAIL.response();
        }
        //添加或删除redis里对应的项目
        item = getById(id);
        if (item.getEnable()){
            Date now = new Date();
            if (now.before(item.getEndTime())){
                redisUtil.itemAdd(item.getId(),item.getEndTime());
            }
        }else {
            redisUtil.itemRemove(item.getId());
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public void setSelectResult(Integer id) {
        List<Course> courseList=  courseMapper.selectList(new QueryWrapper<Course>().eq("item_id",id));
        courseList.forEach(course -> iUserCourseService.setSelectStatusByCourse(course.getId()));
    }

}
