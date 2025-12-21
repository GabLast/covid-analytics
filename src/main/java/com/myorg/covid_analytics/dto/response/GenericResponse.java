package com.myorg.covid_analytics.dto.response;

import lombok.Builder;

@Builder
public record GenericResponse(
        ResponseInfo responseInfo
) {
}
