package com.myorg.covid_analytics.dto.response.configuration;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CountryFindAllData(List<CountryFindAllDataDetails> dataList) implements
        JsonResponse {
}
