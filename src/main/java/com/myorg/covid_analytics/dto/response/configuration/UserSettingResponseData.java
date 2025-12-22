package com.myorg.covid_analytics.dto.response.configuration;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record UserSettingResponseData(
        Long id,
        String timeZoneString,
        String dateFormat,
        String dateTimeFormat,
        boolean darkMode,
        String language
) implements JsonResponse {
}
