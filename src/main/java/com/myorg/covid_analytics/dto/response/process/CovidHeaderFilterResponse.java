package com.myorg.covid_analytics.dto.response.process;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CovidHeaderFilterResponse(CovidHeaderFilterData data) implements
        JsonResponse {
}
