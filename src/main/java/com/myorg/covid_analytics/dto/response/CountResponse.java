package com.myorg.covid_analytics.dto.response;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountResponse(CountResponseData data) implements JsonResponse {
}
