package com.ingreedy.core.service;

import com.ingreedy.core.config.CacheConfig;
import com.ingreedy.core.model.Recommendation;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Recommendation> getRecommendation(String id) {
        Cache cache = cacheManager.getCache(CacheConfig.RECOMMENDATION_CACHE);

        if (cache == null) return Optional.empty();

        Recommendation recommendation = cache.get(id, Recommendation.class);
        return Optional.ofNullable(recommendation);
    }

    public void cacheRecommendation(Recommendation recommendation) {
        Cache cache = cacheManager.getCache(CacheConfig.RECOMMENDATION_CACHE);

        if (cache == null) return;

        cache.put(recommendation.id(), recommendation);
    }
}