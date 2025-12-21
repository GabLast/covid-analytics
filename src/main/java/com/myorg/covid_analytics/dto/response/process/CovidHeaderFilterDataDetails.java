package com.myorg.covid_analytics.dto.response.process;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CovidHeaderFilterDataDetails(Long id, String description, String loadDate, String userName, Long userId) {
}
