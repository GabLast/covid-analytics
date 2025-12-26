package com.myorg.covid_analytics.controller.configuration;

import com.myorg.covid_analytics.dto.request.configuration.CountryFilterRequest;
import com.myorg.covid_analytics.dto.request.configuration.CountryRequest;
import com.myorg.covid_analytics.services.configuration.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService service;

    @GetMapping("fetch")
    public ResponseEntity<?> fetch() {
        return new ResponseEntity<>(service.findAllResponse(), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_COUNTRY)")
    @GetMapping("findall")
    public ResponseEntity<?> findall(CountryFilterRequest request) {
        return new ResponseEntity<>(service.findAllFilter(request), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_COUNTRY)")
    @GetMapping("countall")
    public ResponseEntity<?> countall(CountryFilterRequest request) {
        return new ResponseEntity<>(service.countAllFilter(request), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).COUNTRY_EDIT, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).COUNTRY_CREATE)")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody CountryRequest request) {

        return new ResponseEntity<>(service.save(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).COUNTRY_EDIT, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).COUNTRY_VIEW)")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long id) {
        return new ResponseEntity<>(service.getCountryById(id), HttpStatus.OK);
    }

}


