package com.lyuan.admin.item_college.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyuan.admin.item.entity.Item;
import com.lyuan.admin.item.mapper.ItemMapper;
import com.lyuan.admin.item_college.entity.ItemCollege;
import com.lyuan.admin.item_college.entity.ItemCollegeUpdateDTO;
import com.lyuan.admin.item_college.mapper.ItemCollegeMapper;
import com.lyuan.admin.item_college.service.IItemCollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
@Service
public class ItemCollegeServiceImpl extends ServiceImpl<ItemCollegeMapper, ItemCollege> implements IItemCollegeService {

    @Resource
    private ItemCollegeMapper itemCollegeMapper;
    @Resource
    private ItemMapper itemMapper;

    @Override
    @Transactional
    public BaseResponse updateItemCollege(ItemCollegeUpdateDTO itemCollegeUpdateDTO) {
        Item item = itemMapper.selectById(itemCollegeUpdateDTO.getItemId());
        if (item==null){
            return ResponseEnum.ITEM_NOT_EXIST.response();
        }
        remove(new QueryWrapper<ItemCollege>().eq("item_id",itemCollegeUpdateDTO.getItemId()));
        itemCollegeMapper.updateItemCollege(itemCollegeUpdateDTO);
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse listByItemId(Integer itemId) {
        List<ItemCollege> list = list(new QueryWrapper<ItemCollege>().eq("item_id",itemId));
        String[] colleges = new String[list.size()];
        for (int i = 0;i<list.size();i++){
            colleges[i] = list.get(i).getCollege();
        }
        return ResponseEnum.SUCCESS.response(colleges);
    }
}
