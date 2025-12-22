package com.myorg.covid_analytics.dto.request.security;

import com.myorg.covid_analytics.dto.JsonRequest;
import com.myorg.covid_analytics.dto.response.security.PermitRow;
import lombok.Builder;

import java.util.List;

@Builder
public record ProfileRequest(Long id, String name, String description, List<PermitRow> permits, List<PermitRow> permitsDelete) implements
        JsonRequest {
}
