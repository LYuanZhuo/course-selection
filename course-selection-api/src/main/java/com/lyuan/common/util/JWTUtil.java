package com.lyuan.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Description: JWT相关工具类
 * @Author: Zhuo Linbo
 * @Date: 2020/2/5 13:49
 */
@Slf4j
public class JWTUtil {
    /**
     * 秘钥
     */
    private final static String SECRETKEY = "LYuan";
    /**
     * 一天的毫秒数
     */
    private final static int ONE_DAY = 1000 * 60 * 60 * 24;

    /**
     * @Description: 签名
     * Params:  用户唯一标识
     * Return: token
     * @Author Zhuo Linbo
     * @Date 2020/2/5 13:55
     */
    public static String sign(int userId,int day) {
        Algorithm algorithm = Algorithm.HMAC256(SECRETKEY);
        String token = JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + day*ONE_DAY))
                .sign(algorithm);
        return token;
    }

    /**
     * @Description: 解码
     * Params:  token
     * Return: userId
     * @Author Zhuo Linbo
     * @Date 2020/2/5 14:02
     */
    public static Integer decode(String token) {
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            int userId = decodedJWT.getClaim("userId").asInt();
            return userId;
        }catch (Exception e){
            log.error("解析token出错!");
            return null;
        }
    }

    /**
     * @Description: 检测token是否过期
     * Params:
     * Return: true:过期
     * @Author Zhuo Linbo
     * @Date 2020/2/5 14:18
     */
    public static boolean checkExpire(String token) {
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            Date date = decodedJWT.getExpiresAt();
            return (new Date()).after(date);
        }catch (Exception e){
            log.error("解析token出错!");
            return true;
        }
    }

}
