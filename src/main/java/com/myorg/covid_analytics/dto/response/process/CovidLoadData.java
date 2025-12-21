package com.myorg.covid_analytics.dto.response.process;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CovidLoadData(
        Long headerId,
        String loadDate,
        String description,
        String jsonURL,
        String jsonString,
        List<CovidLoadDataDetail> details
) implements JsonResponse {
}
