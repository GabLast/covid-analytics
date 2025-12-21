package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.services.security.PermitService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

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


