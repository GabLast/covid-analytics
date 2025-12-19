package com.myorg.covid_analytics.controller.process;

import com.myorg.covid_analytics.dto.request.process.CovidLoadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/load")
@RequiredArgsConstructor
public class CovidLoadController {



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfile(
            @RequestPart("user") CovidLoadRequest request,
            @RequestPart("file") MultipartFile file) {



        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}


