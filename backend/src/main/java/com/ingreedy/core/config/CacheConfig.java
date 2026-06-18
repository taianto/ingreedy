package com.ingreedy.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String RECOMMENDATION_CACHE = "recommendationCache";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager =
                new CaffeineCacheManager(RECOMMENDATION_CACHE);

        manager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(10_000)
                        .expireAfterWrite(Duration.ofMinutes(30))
        );

        return manager;
    }
}