package com.myorg.covid_analytics.controller.process;

import com.myorg.covid_analytics.dto.request.process.CovidDetailFilterRequest;
import com.myorg.covid_analytics.dto.request.process.CovidHeaderFilterRequest;
import com.myorg.covid_analytics.dto.request.process.CovidLoadRequest;
import com.myorg.covid_analytics.dto.response.process.CovidDetailFilterData;
import com.myorg.covid_analytics.services.process.CovidAnalyticsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/load")
@RequiredArgsConstructor
public class CovidLoadController {

    private final CovidAnalyticsService covidAnalyticsService;

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_CREATE, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_EDIT)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> load(
            HttpServletRequest req,
            @RequestPart("metadata") CovidLoadRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        int contentLength = req.getContentLength();
        long contentLengthLong = req.getContentLengthLong(); // Use this for larger sizes
        System.out.println("Request size in bytes: " + contentLength);

        return new ResponseEntity<>(covidAnalyticsService.save(request, file),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_DELETE)")
    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return new ResponseEntity<>(covidAnalyticsService.delete(id), HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_EDIT)")
    @GetMapping
    public ResponseEntity<?> getHeaderData(@RequestParam Long id) {
        return new ResponseEntity<>(covidAnalyticsService.getHeaderData(id),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_EDIT)")
    @GetMapping("findall")
    public ResponseEntity<?> findall(CovidHeaderFilterRequest request) {
        return new ResponseEntity<>(covidAnalyticsService.findAllHeaderFilter(request),
                HttpStatus.OK);
    }
    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_EDIT)")
    @GetMapping("countall")
    public ResponseEntity<?> countall(CovidHeaderFilterRequest request) {
        return new ResponseEntity<>(covidAnalyticsService.countAllHeaderFilter(request),
                HttpStatus.OK);
    }

    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_EDIT)")
    @GetMapping("details/findall")
    public ResponseEntity<?> detailsfindall(CovidDetailFilterRequest request) {
        return new ResponseEntity<>(covidAnalyticsService.findAllDetailFilter(request),
                HttpStatus.OK);
    }
    @PreAuthorize(
            "@securityUtils.isAccessGranted(T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_VIEW, "
                    + "T(com.myorg.covid_analytics.models.security.Permit).LOAD_COVID_DATA_EDIT)")
    @GetMapping("details/countall")
    public ResponseEntity<?> detailscountall(CovidDetailFilterRequest request) {
        return new ResponseEntity<>(covidAnalyticsService.countAllDetailFilter(request),
                HttpStatus.OK);
    }

}


