package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.dto.request.security.LoginRequest;
import com.myorg.covid_analytics.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
            throws NoSuchAlgorithmException {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }

}


