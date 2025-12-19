package com.myorg.covid_analytics.services.process;

import com.myorg.covid_analytics.dto.request.process.CovidLoadRequest;
import com.myorg.covid_analytics.dto.response.process.CovidLoadResponse;
import com.myorg.covid_analytics.services.configuration.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CovidAnalyticsService {

    private final CovidLoadHeaderService covidLoadHeaderService;
    private final CovidLoadDetailsService covidLoadDetailsService;
    private final CountryService countryService;

    public CovidLoadResponse save(CovidLoadRequest request) {
        return null;
    }
}
