package com.myorg.covid_analytics.dto.response.configuration;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountryFilterResponse(CountryFilterData data) implements
        JsonResponse {
}
