package com.example.rarelystylebe.domain.services;

public interface RedisService {

    void saveData(String key, Object value);

    void saveDataWithTTL(String key, Object value, long seconds);

    Object getData(String key);

    void deleteData(String key);

    boolean hasKey(String key);
}
