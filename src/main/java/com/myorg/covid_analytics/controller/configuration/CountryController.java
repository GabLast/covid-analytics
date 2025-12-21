package com.myorg.covid_analytics.controller.configuration;

import com.myorg.covid_analytics.services.configuration.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService service;

    @GetMapping("/findall")
    public ResponseEntity<?> findall() {
        return new ResponseEntity<>(service.findAllResponse(), HttpStatus.OK);
    }

}


