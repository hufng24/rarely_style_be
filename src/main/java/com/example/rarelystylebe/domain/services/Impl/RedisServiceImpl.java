package com.example.rarelystylebe.domain.services.Impl;


import com.example.rarelystylebe.domain.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveDataWithTTL(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    @Override
    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
