package com.myorg.covid_analytics.dto.response.process;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CovidHeaderFilterDataDetails(Long id, String description, String loadDate, String userName, Long userId) implements
        JsonResponse {
}
