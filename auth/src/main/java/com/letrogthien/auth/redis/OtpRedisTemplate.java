package com.letrogthien.auth.redis;

import com.letrogthien.auth.otp.OtpModel;
import org.springframework.data.redis.core.RedisTemplate;

public class OtpRedisTemplate extends RedisTemplate<String, OtpModel> {
}
