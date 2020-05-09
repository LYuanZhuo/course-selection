package com.lyuan.common.timedTask;

import com.lyuan.admin.item.service.IItemService;
import com.lyuan.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/29 13:03
 */
@Component
@Slf4j
public class SelectCourseTask {

    @Autowired
    private IItemService iItemService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置选课结果定时器
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    void setSelectResult(){
        Map<Integer,Date> map = redisUtil.itemGetAll();
        log.info("选课结果定时器每隔10s运行中，当前活跃选课项目数："+map.size());
        for (Map.Entry<Integer,Date> entry:map.entrySet()){
            Date now = new Date();
            int itemId = entry.getKey();
            Date endTime = entry.getValue();
            if (now.after(endTime)){
                log.info("项目id:"+itemId+"选课结果执行中");
                iItemService.setSelectResult(itemId);
                redisUtil.itemRemove(itemId);
            }
        }
    }
}
