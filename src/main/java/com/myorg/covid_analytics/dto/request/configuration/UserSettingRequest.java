package com.myorg.covid_analytics.dto.request.configuration;

import com.myorg.covid_analytics.dto.JsonRequest;
import lombok.Builder;

@Builder
public record UserSettingRequest(
        Long id,
        String timeZoneString,
        String dateFormat,
        String dateTimeFormat,
        boolean darkMode,
        String language
) implements JsonRequest {
}
