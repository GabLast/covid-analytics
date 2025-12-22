package com.myorg.covid_analytics.controller.configuration;

import com.myorg.covid_analytics.dto.request.configuration.UserSettingRequest;
import com.myorg.covid_analytics.services.configuration.UserSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/setting")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserSettingService service;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody UserSettingRequest request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(service.getFromUser(), HttpStatus.OK);
    }

}


