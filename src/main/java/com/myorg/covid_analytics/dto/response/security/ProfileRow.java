package com.myorg.covid_analytics.dto.response.security;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record ProfileRow(Long profileUserId, Long id, String profile) implements JsonResponse {
}
