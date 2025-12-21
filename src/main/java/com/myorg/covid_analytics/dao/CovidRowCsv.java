package com.myorg.covid_analytics.dao;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CovidRowCsv {
    @CsvBindByName
    private String     location_key;
    @CsvDate("yyyy-MM-dd")
    @CsvBindByName
    private LocalDate  date;
    @CsvBindByName
    private String     place_id;
    @CsvBindByName
    private String     wikidata_id;
    @CsvBindByName
    private String     datacommons_id;
    @CsvBindByName
    private String     country_code;
    @CsvBindByName
    private String     country_name;
    @CsvBindByName
    private String     iso_3166_1_alpha_2;
    @CsvBindByName
    private String     iso_3166_1_alpha_3;
    @CsvBindByName
    private BigDecimal aggregation_level;
    @CsvBindByName
    private BigDecimal new_confirmed;
    @CsvBindByName
    private BigDecimal new_deceased;
    @CsvBindByName
    private BigDecimal new_tested;
    @CsvBindByName
    private BigDecimal cumulative_confirmed;
    @CsvBindByName
    private BigDecimal cumulative_deceased;
    @CsvBindByName
    private BigDecimal cumulative_tested;
    @CsvBindByName
    private BigDecimal new_persons_vaccinated;
    @CsvBindByName
    private BigDecimal cumulative_persons_vaccinated;
    @CsvBindByName
    private BigDecimal new_persons_fully_vaccinated;
    @CsvBindByName
    private BigDecimal cumulative_persons_fully_vaccinated;
    @CsvBindByName
    private BigDecimal new_vaccine_doses_administered;
    @CsvBindByName
    private BigDecimal cumulative_vaccine_doses_administered;
    @CsvBindByName
    private BigDecimal population;
    @CsvBindByName
    private BigDecimal population_male;
    @CsvBindByName
    private BigDecimal population_female;
    @CsvBindByName
    private BigDecimal population_rural;
    @CsvBindByName
    private BigDecimal population_urban;
    @CsvBindByName
    private BigDecimal population_largest_city;
    @CsvBindByName
    private BigDecimal population_clustered;
    @CsvBindByName
    private BigDecimal population_density;
    @CsvBindByName
    private BigDecimal human_development_index;
    @CsvBindByName
    private BigDecimal population_age_00_09;
    @CsvBindByName
    private BigDecimal population_age_10_19;
    @CsvBindByName
    private BigDecimal population_age_20_29;
    @CsvBindByName
    private BigDecimal population_age_30_39;
    @CsvBindByName
    private BigDecimal population_age_40_49;
    @CsvBindByName
    private BigDecimal population_age_50_59;
    @CsvBindByName
    private BigDecimal population_age_60_69;
    @CsvBindByName
    private BigDecimal population_age_70_79;
    @CsvBindByName
    private BigDecimal population_age_80_and_older;
    @CsvBindByName
    private BigDecimal gdp_usd;
    @CsvBindByName
    private BigDecimal gdp_per_capita_usd;
    @CsvBindByName
    private BigDecimal human_capital_index;
    @CsvBindByName
    private Long       openstreetmap_id;
    @CsvBindByName
    private BigDecimal latitude;
    @CsvBindByName
    private BigDecimal longitude;
    @CsvBindByName
    private BigDecimal area_sq_km;
    @CsvBindByName
    private BigDecimal area_rural_sq_km;
    @CsvBindByName
    private BigDecimal area_urban_sq_km;
    @CsvBindByName
    private BigDecimal life_expectancy;
    @CsvBindByName
    private BigDecimal smoking_prevalence;
    @CsvBindByName
    private BigDecimal diabetes_prevalence;
    @CsvBindByName
    private BigDecimal infant_mortality_rate;
    @CsvBindByName
    private BigDecimal adult_male_mortality_rate;
    @CsvBindByName
    private BigDecimal adult_female_mortality_rate;
    @CsvBindByName
    private BigDecimal pollution_mortality_rate;
    @CsvBindByName
    private BigDecimal comorbidity_mortality_rate;
    @CsvBindByName
    private BigDecimal nurses_per_1000;
    @CsvBindByName
    private BigDecimal physicians_per_1000;
    @CsvBindByName
    private BigDecimal health_expenditure_usd;
    @CsvBindByName
    private BigDecimal out_of_pocket_health_expenditure_usd;
    @CsvBindByName
    private BigDecimal mobility_retail_and_recreation;
    @CsvBindByName
    private BigDecimal mobility_grocery_and_pharmacy;
    @CsvBindByName
    private BigDecimal mobility_parks;
    @CsvBindByName
    private BigDecimal mobility_transit_stations;
    @CsvBindByName
    private BigDecimal mobility_workplaces;
    @CsvBindByName
    private BigDecimal mobility_residential;
    @CsvBindByName
    private BigDecimal school_closing;
    @CsvBindByName
    private BigDecimal workplace_closing;
    @CsvBindByName
    private BigDecimal cancel_public_events;
    @CsvBindByName
    private BigDecimal restrictions_on_gatherings;
    @CsvBindByName
    private BigDecimal public_transport_closing;
    @CsvBindByName
    private BigDecimal stay_at_home_requirements;
    @CsvBindByName
    private BigDecimal restrictions_on_internal_movement;
    @CsvBindByName
    private BigDecimal international_travel_controls;
    @CsvBindByName
    private BigDecimal income_support;
    @CsvBindByName
    private BigDecimal debt_relief;
    @CsvBindByName
    private BigDecimal fiscal_measures;
    @CsvBindByName
    private BigDecimal international_support;
    @CsvBindByName
    private BigDecimal public_information_campaigns;
    @CsvBindByName
    private BigDecimal testing_policy;
    @CsvBindByName
    private BigDecimal contact_tracing;
    @CsvBindByName
    private BigDecimal emergency_investment_in_healthcare;
    @CsvBindByName
    private BigDecimal investment_in_vaccines;
    @CsvBindByName
    private BigDecimal facial_coverings;
    @CsvBindByName
    private BigDecimal vaccination_policy;
    @CsvBindByName
    private BigDecimal stringency_index;
    @CsvBindByName
    private BigDecimal average_temperature_celsius;
    @CsvBindByName
    private BigDecimal minimum_temperature_celsius;
    @CsvBindByName
    private BigDecimal maximum_temperature_celsius;
    @CsvBindByName
    private BigDecimal rainfall_mm;
    @CsvBindByName
    private BigDecimal snowfall_mm;
    @CsvBindByName
    private BigDecimal dew_point;
    @CsvBindByName
    private BigDecimal relative_humidity;

}
