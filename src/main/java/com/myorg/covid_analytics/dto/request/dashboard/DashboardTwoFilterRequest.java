package com.myorg.covid_analytics.dto.request.dashboard;

import com.myorg.covid_analytics.dto.JsonRequest;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DashboardTwoFilterRequest(
        String country, LocalDate dateStart, LocalDate dateEnd
) implements JsonRequest {

}
