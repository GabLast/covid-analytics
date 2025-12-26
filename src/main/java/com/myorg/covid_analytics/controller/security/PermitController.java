package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.services.security.PermitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permits")
@RequiredArgsConstructor
public class PermitController {

    private final PermitService permitService;

    @GetMapping("fetch")
    public ResponseEntity<?> findallfetch() {
        return new ResponseEntity<>(permitService.fetchPermits(), HttpStatus.OK);
    }
}


