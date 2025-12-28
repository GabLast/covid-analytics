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
    private BigDecimal aggregation_level = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal new_confirmed = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal new_deceased = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal new_tested = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cumulative_confirmed = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cumulative_deceased = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cumulative_tested = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal new_persons_vaccinated = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cumulative_persons_vaccinated = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal new_persons_fully_vaccinated = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cumulative_persons_fully_vaccinated = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal new_vaccine_doses_administered = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cumulative_vaccine_doses_administered = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_male = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_female = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_rural = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_urban = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_largest_city = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_clustered = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_density = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal human_development_index = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_00_09 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_10_19 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_20_29 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_30_39 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_40_49 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_50_59 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_60_69 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_70_79 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal population_age_80_and_older = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal gdp_usd = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal gdp_per_capita_usd = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal human_capital_index = BigDecimal.ZERO;
    @CsvBindByName
    private Long       openstreetmap_id;
    @CsvBindByName
    private BigDecimal latitude = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal longitude = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal area_sq_km = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal area_rural_sq_km = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal area_urban_sq_km = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal life_expectancy = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal smoking_prevalence = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal diabetes_prevalence = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal infant_mortality_rate = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal adult_male_mortality_rate = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal adult_female_mortality_rate = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal pollution_mortality_rate = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal comorbidity_mortality_rate = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal nurses_per_1000 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal physicians_per_1000 = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal health_expenditure_usd = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal out_of_pocket_health_expenditure_usd = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal mobility_retail_and_recreation = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal mobility_grocery_and_pharmacy = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal mobility_parks = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal mobility_transit_stations = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal mobility_workplaces = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal mobility_residential = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal school_closing = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal workplace_closing = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal cancel_public_events = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal restrictions_on_gatherings = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal public_transport_closing = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal stay_at_home_requirements = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal restrictions_on_internal_movement = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal international_travel_controls = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal income_support = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal debt_relief = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal fiscal_measures = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal international_support = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal public_information_campaigns = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal testing_policy = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal contact_tracing = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal emergency_investment_in_healthcare = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal investment_in_vaccines = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal facial_coverings = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal vaccination_policy = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal stringency_index = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal average_temperature_celsius = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal minimum_temperature_celsius = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal maximum_temperature_celsius = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal rainfall_mm = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal snowfall_mm = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal dew_point = BigDecimal.ZERO;
    @CsvBindByName
    private BigDecimal relative_humidity = BigDecimal.ZERO;

}
