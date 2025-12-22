package com.myorg.covid_analytics.dto.request.security;

import com.myorg.covid_analytics.dto.JsonRequest;
import com.myorg.covid_analytics.dto.response.security.ProfileRow;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(
        Long id,
        String username,
        String password,
        String name,
        String email,
        boolean admin,
        List<ProfileRow> profiles,
        List<ProfileRow> profilesDelete
) implements JsonRequest {
}
