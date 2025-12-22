package com.myorg.covid_analytics.dto.response.security;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record PermitDataDetails(Long id, String permit, String fatherCode) implements
        JsonResponse {
}
