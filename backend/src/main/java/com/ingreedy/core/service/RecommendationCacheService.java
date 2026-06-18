package com.ingreedy.core.service;

import com.ingreedy.core.config.CacheConfig;
import com.ingreedy.core.model.Recommendation;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationCacheService {

    private final CacheManager cacheManager;

    public RecommendationCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void cacheRecommendations(List<Recommendation> recommendations) {
        Cache cache = cacheManager.getCache(CacheConfig.RECOMMENDATION_CACHE);

        if (cache == null) return;

        recommendations.forEach(r -> cache.put(r.id(), r));
    }
}