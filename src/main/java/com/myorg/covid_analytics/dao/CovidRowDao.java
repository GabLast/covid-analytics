package com.myorg.covid_analytics.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CovidRowDao(
        String countryCode
        ,LocalDate date
) {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CovidRowDao(
            @JsonProperty(index = 0) String countryCode,
            @JsonProperty(index = 1) LocalDate date

    ) {
        this.countryCode = countryCode;
        this.date = date;
    }

    //ObjectMapper mapper = new ObjectMapper();
    //mapper.registerModule(new JavaTimeModule());
}
