package com.myorg.covid_analytics.dto.response.configuration;

import lombok.Builder;

@Builder
public record CountryFindAllDataDetails(Long id, String name, String code) {
}
