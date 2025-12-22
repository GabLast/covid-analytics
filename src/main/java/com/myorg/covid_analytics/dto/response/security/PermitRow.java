package com.myorg.covid_analytics.dto.response.security;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record PermitRow(Long profilePermitId, Long id, String permit, String code) implements JsonResponse {
}
