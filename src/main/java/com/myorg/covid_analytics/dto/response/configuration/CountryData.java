package com.myorg.covid_analytics.dto.response.configuration;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountryData(
        Long id,
        String name,
        String code,
        String placeId,
        String wikiDataId,
        String dataCommonsId,
        String iso_3166_1_alpha_2,
        String iso_3166_1_alpha_3
) implements JsonResponse {
}
