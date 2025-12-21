package com.myorg.covid_analytics.dto.response.security;

import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record PermitData(List<String> permits) {
}
