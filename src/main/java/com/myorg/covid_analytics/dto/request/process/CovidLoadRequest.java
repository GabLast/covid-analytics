package com.myorg.covid_analytics.dto.request.process;

import com.myorg.covid_analytics.dto.JsonRequest;

import java.time.LocalDate;

public record CovidLoadRequest(Long userId, LocalDate date, String description, String json, String jsonURL)
        implements JsonRequest {
}
