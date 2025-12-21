package com.myorg.covid_analytics.dto.response.dashboard;

import com.myorg.covid_analytics.dto.JsonResponse;
import com.myorg.covid_analytics.dto.response.ResponseInfo;
import lombok.Builder;

@Builder
public record DashboardOneResponse(
        DashboardOneData data,
        ResponseInfo responseInfo
) implements JsonResponse {
}
