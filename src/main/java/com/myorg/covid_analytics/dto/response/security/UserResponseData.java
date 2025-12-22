package com.myorg.covid_analytics.dto.response.security;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponseData(
        Long id,
        String username,
        String name,
        String mail,
        String password,
        boolean admin,
        List<ProfileRow> profiles
) implements JsonResponse {
}
