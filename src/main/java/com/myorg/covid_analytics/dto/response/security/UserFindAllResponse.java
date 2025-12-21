package com.myorg.covid_analytics.dto.response.security;

import lombok.Builder;

@Builder
public record UserFindAllResponse(UserFindAllData data) {
}
