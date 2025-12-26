package com.myorg.covid_analytics.async;

import com.myorg.covid_analytics.services.process.CovidAnalyticsService;
import com.myorg.covid_analytics.services.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CronJob {

    private final CovidAnalyticsService covidAnalyticsService;
    private final RedisService          redisService;

    //60 = X = minutes
    @Scheduled(fixedDelay = 60 * 1000 * 60) // every X minutes
    public void setDashboardOneDataInCache() throws InterruptedException {
        log.info("Executing Cron Job: setDashboardOneDataInCache");
        redisService.setDashboardOneCache(covidAnalyticsService.getDataDashboardOne(true));
    }
}
