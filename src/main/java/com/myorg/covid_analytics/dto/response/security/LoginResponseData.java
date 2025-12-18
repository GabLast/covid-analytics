package com.myorg.covid_analytics.dto.response.security;

import lombok.Builder;

import java.util.List;

@Builder
public record LoginResponseData(String token, List<PermitDto> grantedAuthorities) {
}
