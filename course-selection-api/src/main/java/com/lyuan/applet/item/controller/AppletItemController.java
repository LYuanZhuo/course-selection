package com.lyuan.applet.item.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyuan.applet.item.entity.ItemListDTO;
import com.lyuan.applet.item.service.IAppletItemService;
import com.lyuan.common.annotation.Interception;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/applet/item")
@Slf4j
public class AppletItemController {
    @Autowired
    IAppletItemService iAppletItemService;

    @Interception
    @PostMapping("/list")
    public BaseResponse list(@RequestBody ItemListDTO listDTO) {
        log.info("小程序查询项目列表{}", JSONObject.toJSONString(listDTO));
        return iAppletItemService.list(listDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("小程序查询项目{}", JSONObject.toJSONString(id));
        return iAppletItemService.get(id);
    }

    @GetMapping("/enterItem/{id}")
    public BaseResponse enterItem(@PathVariable Integer id,HttpServletRequest request) {
        log.info("小程序查询用户能否进入项目{}", JSONObject.toJSONString(id));
        return iAppletItemService.enterItem(id,request);
    }

}
