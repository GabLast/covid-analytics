package com.myorg.covid_analytics.dto.response.configuration;

import lombok.Builder;

import java.util.List;

@Builder
public record CountryFindAllData(List<CountryFindAllDataDetails> dataList) {
}
