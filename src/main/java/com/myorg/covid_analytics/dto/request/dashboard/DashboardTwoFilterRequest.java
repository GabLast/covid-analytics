package com.myorg.covid_analytics.dto.request.dashboard;

import com.myorg.covid_analytics.dto.JsonRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DashboardTwoFilterRequest implements JsonRequest {
    private String    country   = null;
    private LocalDate dateStart = null;
    private LocalDate dateEnd   = null;
}
