package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.dto.request.security.ProfileFilterRequest;
import com.myorg.covid_analytics.dto.request.security.ProfileRequest;
import com.myorg.covid_analytics.services.security.ProfileService;
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
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).PROFILE_CREATE, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).PROFILE_EDIT)")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProfileRequest request) {

        return new ResponseEntity<>(service.saveProfileRequest(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_PROFILE)")
    @GetMapping("findall")
    public ResponseEntity<?> findall(ProfileFilterRequest request) {
        return new ResponseEntity<>(service.findAllProfileFilter(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).MENU_PROFILE)")
    @GetMapping("countall")
    public ResponseEntity<?> countall(ProfileFilterRequest request) {
        return new ResponseEntity<>(service.countAllProfileFilter(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).PROFILE_DELETE)")
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).PROFILE_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).PROFILE_EDIT)")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long id) {
        return new ResponseEntity<>(service.getProfileById(id), HttpStatus.OK);
    }

    @GetMapping("fetch")
    public ResponseEntity<?> findallfetch() {
        return new ResponseEntity<>(service.fetchProfiles(), HttpStatus.OK);
    }
}


