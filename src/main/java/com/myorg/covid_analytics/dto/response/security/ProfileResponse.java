package com.myorg.covid_analytics.dto.response.security;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record ProfileResponse(ProfileResponseData data) implements JsonResponse {
}
