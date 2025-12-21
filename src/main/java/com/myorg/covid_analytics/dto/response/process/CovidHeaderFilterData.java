package com.myorg.covid_analytics.dto.response.process;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CovidHeaderFilterData(List<CovidHeaderFilterDataDetails> dataList) implements
        JsonResponse {
}
