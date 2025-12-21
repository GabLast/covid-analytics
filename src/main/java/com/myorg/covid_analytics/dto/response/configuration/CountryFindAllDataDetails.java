package com.myorg.covid_analytics.dto.response.configuration;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountryFindAllDataDetails(Long id, String name, String code) implements
        JsonResponse {
}
