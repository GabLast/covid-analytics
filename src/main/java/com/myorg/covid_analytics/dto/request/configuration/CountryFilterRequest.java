package com.myorg.covid_analytics.dto.request.configuration;

import com.myorg.covid_analytics.dto.JsonRequest;
import com.myorg.covid_analytics.dto.request.RequestPagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CountryFilterRequest extends RequestPagination implements JsonRequest {
    private boolean enabled     = true;
    private String  name        = null;
    private String  countryCode = null;
}
