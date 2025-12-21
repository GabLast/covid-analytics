package com.myorg.covid_analytics.dto.response.dashboard;

import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DashboardTwoData(
        BigDecimal infections,
        BigDecimal deaths,
        BigDecimal newPersonVaccinated,
        BigDecimal newPersonFullyVaccinated,
        BigDecimal newTested,
        BigDecimal newVaccineDosesAdministered
) implements JsonResponse {
}
