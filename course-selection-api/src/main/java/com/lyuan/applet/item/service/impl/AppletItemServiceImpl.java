package com.lyuan.applet.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.item_college.entity.ItemCollege;
import com.lyuan.admin.item_college.service.IItemCollegeService;
import com.lyuan.applet.item.entity.Item;
import com.lyuan.applet.item.entity.ItemListDTO;
import com.lyuan.applet.item.entity.ItemVO;
import com.lyuan.applet.item.mapper.AppletItemMapper;
import com.lyuan.applet.item.service.IAppletItemService;
import com.lyuan.applet.user.entity.User;
import com.lyuan.applet.user.service.IAppletUserService;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.JWTUtil;
import com.lyuan.common.util.ListQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class AppletItemServiceImpl extends ServiceImpl<AppletItemMapper, Item> implements IAppletItemService {

    @Resource
    AppletItemMapper appletItemMapper;
    @Resource
    IItemCollegeService iItemCollegeService;
    @Resource
    IAppletUserService iAppletUserService;

    @Override
    public BaseResponse list(ItemListDTO listDTO) {
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(listDTO.getName())){
            wrapper.like("name",listDTO.getName());
        }
        wrapper.eq("enable",true);
        ListQueryUtil.orderHandler(wrapper,listDTO);
        List<Item> list = list(wrapper);
        List<ItemVO> resultList = new ArrayList<>(list.size());
        list.forEach(item->{
            ItemVO itemVo = new ItemVO();
            BeanUtils.copyProperties(item,itemVo);
            setItemStatus(itemVo);
            resultList.add(itemVo);
        });
        return ResponseEnum.SUCCESS.responseList(resultList,1, resultList.size());
    }

    /**
     * @param
     * *设置项目状态
     */
    private void setItemStatus(ItemVO itemVo){
        Date now = new Date();
        if (now.before(itemVo.getStartTime())){
            itemVo.setStatus("未开始");
        }
        if (now.after(itemVo.getStartTime())&&now.before(itemVo.getEndTime())){
            itemVo.setStatus("进行中");
        }
        if (now.after(itemVo.getEndTime())){
            itemVo.setStatus("已结束");
        }
    }

    @Override
    public BaseResponse get(Integer id) {
        Item item = getById(id);
        if (item == null) {
            return ResponseEnum.FAIL.response();
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item,itemVO);
        setItemStatus(itemVO);
        return ResponseEnum.SUCCESS.response(itemVO);
    }

    @Override
    public BaseResponse enterItem(Integer id, HttpServletRequest request) {
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        User user = iAppletUserService.getById(userId);
        Item item = appletItemMapper.selectById(id);
        List<String> gradeList = Arrays.asList(item.getGrades().split(","));
        if (!gradeList.contains(user.getGrade().toString())){
            return ResponseEnum.ITEM_ENTER_ERROR.response();
        }
        boolean enter = false;
        String college = user.getCollege();
        List<ItemCollege> list = iItemCollegeService.list(new QueryWrapper<ItemCollege>().eq("item_id",id));
        for (ItemCollege itemCollege :list){
            if (itemCollege.getCollege().equals(college)){
                enter = true;
            }
        }
        if (!enter){
            return ResponseEnum.ITEM_ENTER_ERROR.response();
        }
        return ResponseEnum.SUCCESS.response();
    }
}
