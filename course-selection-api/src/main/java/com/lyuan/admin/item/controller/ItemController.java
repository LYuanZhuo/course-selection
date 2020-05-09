package com.lyuan.admin.item.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.item.entity.Item;
import com.lyuan.admin.item.entity.ItemListDTO;
import com.lyuan.admin.item.service.IItemService;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/item")
@Slf4j
public class ItemController {
    @Autowired
    IItemService iItemService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody ItemListDTO listDTO) {
        log.info("查询项目列表{}", JSONObject.toJSONString(listDTO));
        return iItemService.list(listDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("查询项目{}", JSONObject.toJSONString(id));
        return iItemService.get(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        log.info("删除Item{}", JSONObject.toJSONString(id));
        return iItemService.delete(id);
    }

    @PostMapping("/")
    public BaseResponse add(@RequestBody Item item) {
        log.info("增添项目{}", JSONObject.toJSONString(item));
        return iItemService.add(item);
    }

    @PutMapping("/")
    public BaseResponse update(@RequestBody Item item) {
        log.info("修改项目{}", JSONObject.toJSONString(item));
        return iItemService.update(item);
    }

    @PutMapping("/enable/{id}")
    public BaseResponse enable(@PathVariable Integer id) {
        log.info("启用/禁用项目{}", JSONObject.toJSONString(id));
        return iItemService.enable(id);
    }
}
