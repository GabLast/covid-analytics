package com.myorg.covid_analytics.dto.response.redis;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CovidTotals() implements JsonResponse {
}
