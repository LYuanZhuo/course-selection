package com.lyuan.common.util;

import com.lyuan.common.define.RedisKeyDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/29 14:09
 */
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void itemRemove(Integer itemId){
        redisTemplate.opsForHash().delete(RedisKeyDefine.ITEMS_KEY,itemId.toString());
    }

    public void itemAdd(Integer itemId, Date endTime){
        redisTemplate.opsForHash().put(RedisKeyDefine.ITEMS_KEY,itemId.toString(),endTime);
    }

    public Map<Integer,Date> itemGetAll(){
        Map<Object,Object> map = redisTemplate.opsForHash().entries(RedisKeyDefine.ITEMS_KEY);
        Map<Integer,Date> resultMap = new HashMap<>(map.size());
        for (Map.Entry entry:map.entrySet()) {
            resultMap.put(Integer.valueOf((String) entry.getKey()),(Date)entry.getValue());
        }
        return resultMap;
    }
}
