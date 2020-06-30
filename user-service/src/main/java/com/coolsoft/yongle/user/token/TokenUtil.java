package com.coolsoft.yongle.user.token;

import com.alibaba.fastjson.JSON;
import com.coolsoft.yongle.user.entity.User;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TokenUtil {

    public static String genToken(RedisTemplate redisTemplate, User user) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("sess_" + token, user.getId());
        redisTemplate.expire("sess_" + token, 30, TimeUnit.DAYS);
        return token;
    }
}
