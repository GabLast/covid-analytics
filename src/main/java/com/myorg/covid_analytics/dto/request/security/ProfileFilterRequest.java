package com.myorg.covid_analytics.dto.request.security;

import com.myorg.covid_analytics.dto.JsonRequest;
import com.myorg.covid_analytics.dto.request.RequestPagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProfileFilterRequest extends RequestPagination implements JsonRequest {
    private boolean   enabled   = true;
    private String    name   = null;
    private String    description   = null;
}
