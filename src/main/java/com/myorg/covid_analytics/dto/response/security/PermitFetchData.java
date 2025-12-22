package com.myorg.covid_analytics.dto.response.security;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record PermitFetchData(List<PermitFetchDetail> permits) implements JsonResponse {
}
