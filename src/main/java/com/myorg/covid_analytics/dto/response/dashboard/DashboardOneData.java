package com.myorg.covid_analytics.dto.response.dashboard;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record DashboardOneData(
        List<DashboardOneDataDetails> details
) implements JsonResponse {
}
