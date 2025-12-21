package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.services.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_USER)")
    @GetMapping("/findall")
    public ResponseEntity<?> findall() {
        return new ResponseEntity<>(userService.findAllResponse(), HttpStatus.OK);
    }

}


