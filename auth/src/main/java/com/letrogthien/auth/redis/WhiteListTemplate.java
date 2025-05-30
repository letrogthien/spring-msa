package com.letrogthien.auth.redis;

import com.letrogthien.auth.entities.WhiteList;
import org.springframework.data.redis.core.RedisTemplate;

public class WhiteListTemplate extends RedisTemplate<String, WhiteList> {
}
