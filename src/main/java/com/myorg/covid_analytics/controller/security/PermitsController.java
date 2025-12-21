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
public class PermitsController {

    private final PermitService permitService;

    @GetMapping
    public ResponseEntity<?> listPermits() {
        return new ResponseEntity<>(permitService.findAllResponse(), HttpStatus.OK);
    }

}


