package com.myorg.covid_analytics.services.redis;

import com.myorg.covid_analytics.dto.response.redis.CovidTotals;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, CovidTotals> covidTotals;

    //Keys
    public static final String DASHBOARD_TAB_1_DATA = "CovidTotals";

    public Optional<CovidTotals> getSimpleDataCountCache() {
        covidTotals.opsForValue();
        //        Integer value = covidTotals.get("");
        //        if (value != null) {
        //            return Optional.of(CovidTotals.builder()
        //                    .build());
        //        } else {
        //            return Optional.empty();
        //        }
        return Optional.empty();
    }

}
