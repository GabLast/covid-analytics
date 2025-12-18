package com.myorg.covid_analytics.dto.response.security;

import lombok.Builder;

import java.util.List;

@Builder
public record LoginResponse(LoginResponseData data) {
}
