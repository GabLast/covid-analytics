package com.myorg.covid_analytics.dto.response.configuration;

import lombok.Builder;

@Builder
public record CountryFindAllResponse(CountryFindAllData data) {
}
