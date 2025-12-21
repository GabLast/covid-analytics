package com.myorg.covid_analytics.dto.response;

import lombok.Builder;

@Builder
public record CountResponse(CountResponseData data) {
}
