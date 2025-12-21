package com.myorg.covid_analytics.dto.request.security;

import com.myorg.covid_analytics.dto.JsonRequest;
import lombok.Builder;

@Builder
public record LoginRequest(Long id, String usernameMail, String password)
        implements JsonRequest {
}
