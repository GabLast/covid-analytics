package com.myorg.covid_analytics.dto.request.process;

import com.myorg.covid_analytics.dto.JsonRequest;

import java.time.LocalDate;

public record CovidLoadRequest(Long id, LocalDate date, String description, String jsonURL, String jsonString)
        implements JsonRequest {
}
