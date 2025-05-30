package com.letrogthien.auth.redis.services;

import com.letrogthien.auth.common.Status;
import com.letrogthien.auth.otp.OtpModel;
import com.letrogthien.auth.otp.OtpType;
import com.letrogthien.auth.redis.OtpRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpModelCacheService {
    private final OtpRedisTemplate redisTemplate;

    private String generateOtpCacheKey(UUID userId, String otp, OtpType otpType) {
        return "otp:" + otp + ":" + otpType.toString() + ":" + userId.toString();
    }

    public void saveOtpModel(OtpModel otpModel) {
        String key = this.generateOtpCacheKey(otpModel.getUserId(), otpModel.getOtp(), otpModel.getOtpType());
        this.redisTemplate.opsForValue().set(key, otpModel);
        this.redisTemplate.expire(key, 5L, TimeUnit.MINUTES);
    }

    public boolean isPresentAndValidInCache(UUID userId, String otp, OtpType otpType) {
        String key = this.generateOtpCacheKey(userId, otp, otpType);
        OtpModel otpModel = this.redisTemplate.opsForValue().get(key);
        if (otpModel == null) {
            return false;
        }
        if (otpModel.getExpiredAt().isBefore(ZonedDateTime.now())||otpModel.getStatus() != Status.ACTIVE) {
            this.redisTemplate.delete(key);
            return false;
        }
        this.redisTemplate.delete(key);
        return true;
    }

}
