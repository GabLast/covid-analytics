package com.myorg.covid_analytics.dto.response.process;

import lombok.Builder;

@Builder
public record CovidDetailFilterResponse(CovidDetailFilterData data) {
}
