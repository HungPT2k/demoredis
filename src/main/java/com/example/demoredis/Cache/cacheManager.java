package com.example.demoredis.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//@Component
public class cacheManager {
//    @Autowired
//    private cacheManager cacheManager;
//    private RedisTemplate<String, Object> redisTemplate;
//
//    public List<Object> getAllMessages() {
//        Set<String> keys = redisTemplate.keys("*");
//        assert keys != null;
//        return redisTemplate.opsForValue().multiGet(keys);
//    }
//
//
//
//    public Object getValueFromCache(String cacheName, String key) {
//        Cache cache = cacheManager.getCache(cacheName);
//        if (cache != null) {
//            Cache.ValueWrapper valueWrapper = cache.get(key);
//            if (valueWrapper != null) {
//                return valueWrapper.get();
//            }
//        }
//        return null;
//    }

}
