package com.myorg.covid_analytics.bootstrap;

import com.myorg.covid_analytics.services.configuration.CountryService;
import com.myorg.covid_analytics.services.security.PermitService;
import com.myorg.covid_analytics.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@RequiredArgsConstructor
@Slf4j
public class Bootstrap implements ApplicationRunner {

    private final UserService   userService;
    private final PermitService  permitService;
    private final CountryService countryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            userService.bootstrap();
            permitService.bootstrap();
            countryService.bootstrap();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
