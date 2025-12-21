package com.myorg.covid_analytics.dto.response.security;

import lombok.Builder;

import java.util.List;

@Builder
public record UserFindAllDataDetails(Long id, String name) {
}
