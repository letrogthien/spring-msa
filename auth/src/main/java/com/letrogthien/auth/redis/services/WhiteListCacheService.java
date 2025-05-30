package com.letrogthien.auth.redis.services;

import com.letrogthien.auth.entities.WhiteList;
import com.letrogthien.auth.redis.WhiteListTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WhiteListCacheService {
    @Value("${security.secret.tokens.refresh_token.expiration}")
    private long refreshExpiration;
    private final WhiteListTemplate redisTemplate;

    private String generateWhiteListCacheKey(String jti, UUID userId) {
        return "whitelist:" + jti + ":" + userId;
    }

    public void saveToCache(WhiteList whiteList) {
        String key = this.generateWhiteListCacheKey(whiteList.getJti(), whiteList.getUserId());
        this.redisTemplate.opsForValue().set(key, whiteList);
        this.redisTemplate.expire(key, this.refreshExpiration, TimeUnit.SECONDS);
    }

    public boolean isPresentInCache(String jti, UUID userId) {
        String key = this.generateWhiteListCacheKey(jti, userId);
        return this.redisTemplate.hasKey(key);
    }

    public void deleteFromCache(String jti, UUID userId) {
        String key = this.generateWhiteListCacheKey(jti, userId);
        this.redisTemplate.delete(key);
    }

    public void deleteAllByUserId(UUID userId) {
        String pattern = "whitelist:*:" + userId;
        Set<String> keys = this.redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            keys.stream()
                .filter(Objects::nonNull)
                .forEach(this.redisTemplate::delete);
        }
    }


}