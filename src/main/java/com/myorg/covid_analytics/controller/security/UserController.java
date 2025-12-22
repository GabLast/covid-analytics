package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.dto.request.security.UserFilterRequest;
import com.myorg.covid_analytics.dto.request.security.UserRequest;
import com.myorg.covid_analytics.services.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("fetch")
    public ResponseEntity<?> findallfetch() {
        return new ResponseEntity<>(userService.fetchUsers(), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).USER_CREATE, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).USER_EDIT)")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody UserRequest request) {

        return new ResponseEntity<>(userService.saveUserRequest(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_USER)")
    @GetMapping("findall")
    public ResponseEntity<?> findallfilter(UserFilterRequest request) {
        return new ResponseEntity<>(userService.findAllUserFilter(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_USER)")
    @GetMapping("countall")
    public ResponseEntity<?> countall(UserFilterRequest request) {
        return new ResponseEntity<>(userService.countAllUserFilter(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).USER_DELETE)")
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).PROFILE_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).USER_EDIT)")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).PROFILE_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).USER_EDIT)")
    @GetMapping("usernameormail")
    public ResponseEntity<?> usernameormail(@RequestParam String usernameormail) {
        return new ResponseEntity<>(userService.getUserByUsername(usernameormail), HttpStatus.OK);
    }
}


