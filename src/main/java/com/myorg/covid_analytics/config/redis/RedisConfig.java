package com.myorg.covid_analytics.config.redis;

import com.myorg.covid_analytics.config.AppInfo;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneResponse;
import com.myorg.covid_analytics.services.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {

    private final AppInfo appInfo;
    //    private final String  HOST = "localhost";
    //    private final String PASSWORD = "localhost";
    //    private final int PORT = 26379;
    //    public static final int TIME_TO_LIVE = 1440; //#1 day;

    @Bean
    protected LettuceConnectionFactory getConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(appInfo.getRedisHost(),
                        appInfo.getRedisPort());
        char[] redisPassword = appInfo.getRedisPassword().toCharArray();

        // Set the password only if it's present (local may not have a password)
        if (redisPassword != null && redisPassword.length > 0) {
            redisStandaloneConfiguration.setPassword(appInfo.getRedisPassword());
        }

        LettuceConnectionFactory redisConnectionFactory =
                new LettuceConnectionFactory(redisStandaloneConfiguration);
        redisConnectionFactory.start();

        return redisConnectionFactory;
    }

    private RedisCacheConfiguration getDefaultCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(appInfo.getRedisTimeToLive())).disableCachingNullValues();
    }

    @Bean
    protected RedisCacheManager redisStarter() {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(getConnectionFactory())
                .withCacheConfiguration(RedisService.DASHBOARD_TAB_1_DATA,
                        getDefaultCacheConfig()).build();
    }

    @Bean
    protected RedisTemplate<String, DashboardOneResponse> covidTotals() {
        RedisTemplate<String, DashboardOneResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
