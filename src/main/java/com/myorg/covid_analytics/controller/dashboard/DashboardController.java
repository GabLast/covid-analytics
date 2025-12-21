package com.myorg.covid_analytics.controller.dashboard;

import com.myorg.covid_analytics.dto.request.dashboard.DashboardTwoFilterRequest;
import com.myorg.covid_analytics.services.process.CovidLoadDetailService;
import com.myorg.covid_analytics.services.security.PermitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final CovidLoadDetailService service;

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).DASHBOARD_TAB_ONE)")
    @GetMapping("/one")
    public ResponseEntity<?> one() {
        return new ResponseEntity<>(service.getDataDashboardOne(), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).DASHBOARD_TAB_TWO)")
    @GetMapping("/two")
    public ResponseEntity<?> two(DashboardTwoFilterRequest request) {
        return new ResponseEntity<>(service.getDataDashboardTwo(request), HttpStatus.OK);
    }

}


