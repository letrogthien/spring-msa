package com.letrogthien.auth.redis;


import com.letrogthien.auth.entities.TrustingDevice;
import org.springframework.data.redis.core.RedisTemplate;


public class DeviceRedisTemplate extends RedisTemplate<String, TrustingDevice> {
}
