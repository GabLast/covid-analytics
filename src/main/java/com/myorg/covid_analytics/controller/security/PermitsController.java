package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.dto.security.LoginRequest;
import com.myorg.covid_analytics.services.security.AuthenticationService;
import com.myorg.covid_analytics.services.security.PermitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/permits")
@RequiredArgsConstructor
public class PermitsController {

    private final PermitService permitService;

    @GetMapping
    public ResponseEntity<?> listPermits()
            throws NoSuchAlgorithmException {
        return new ResponseEntity<>(permitService.findAllByEnabled(true), HttpStatus.OK);
    }

}


