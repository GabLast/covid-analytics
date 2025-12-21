package com.myorg.covid_analytics.dto.response.process;

import lombok.Builder;

import java.util.List;

@Builder
public record CovidHeaderFilterData(List<CovidHeaderFilterDataDetails> dataList) {
}
