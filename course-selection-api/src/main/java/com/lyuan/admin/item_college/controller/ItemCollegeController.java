package com.lyuan.admin.item_college.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.item_college.entity.ItemCollegeUpdateDTO;
import com.lyuan.admin.item_college.service.IItemCollegeService;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/item_college")
@Slf4j
public class ItemCollegeController {
    @Autowired
    private IItemCollegeService iItemCollegeService;

    @PutMapping("/updateItemCollege")
    public BaseResponse updateItemCollege(@RequestBody ItemCollegeUpdateDTO itemCollegeUpdateDTO){
        log.info("修改项目与学院关联{}", JSONObject.toJSONString(itemCollegeUpdateDTO));
        return iItemCollegeService.updateItemCollege(itemCollegeUpdateDTO);
    }

    @GetMapping("/listByItemId/{itemId}")
    public BaseResponse listByItemId(@PathVariable Integer itemId){
        log.info("获取项目的所有关联", JSONObject.toJSONString(itemId));
        return iItemCollegeService.listByItemId(itemId);
    }
}
