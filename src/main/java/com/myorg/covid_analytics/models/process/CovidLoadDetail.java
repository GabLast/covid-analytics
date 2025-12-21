package com.myorg.covid_analytics.models.process;

import com.myorg.covid_analytics.models.BaseModel;
import com.myorg.covid_analytics.models.configurations.Country;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class CovidLoadDetail extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CovidLoadHeader header;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;
    private LocalDate date;

    private BigDecimal new_tested;
    private BigDecimal new_confirmed;
    private BigDecimal new_deceased;
    private BigDecimal new_persons_vaccinated;
    private BigDecimal new_persons_fully_vaccinated;
    private BigDecimal new_vaccine_doses_administered;
    private BigDecimal population;
    private BigDecimal population_male;
    private BigDecimal population_female;
    private BigDecimal population_rural;
    private BigDecimal population_urban;
    private BigDecimal population_largest_city;
    private BigDecimal population_clustered;
    private BigDecimal population_density;
    private BigDecimal human_development_index;
    private BigDecimal population_age_00_09;
    private BigDecimal population_age_10_19;
    private BigDecimal population_age_20_29;
    private BigDecimal population_age_30_39;
    private BigDecimal population_age_40_49;
    private BigDecimal population_age_50_59;
    private BigDecimal population_age_60_69;
    private BigDecimal population_age_70_79;
    private BigDecimal population_age_80_and_older;
    private BigDecimal gdp_usd;
    private BigDecimal gdp_per_capita_usd;
    private BigDecimal human_capital_index;
    private Long openstreetmap_id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal area_sq_km;
    private BigDecimal area_rural_sq_km;
    private BigDecimal area_urban_sq_km;
    private BigDecimal life_expectancy;
    private BigDecimal smoking_prevalence;
    private BigDecimal diabetes_prevalence;
    private BigDecimal infant_mortality_rate;
    private BigDecimal adult_male_mortality_rate;
    private BigDecimal adult_female_mortality_rate;
    private BigDecimal pollution_mortality_rate;
    private BigDecimal comorbidity_mortality_rate;
    private BigDecimal nurses_per_1000;
    private BigDecimal physicians_per_1000;
    private BigDecimal health_expenditure_usd;
    private BigDecimal out_of_pocket_health_expenditure_usd;
    private BigDecimal mobility_retail_and_recreation;
    private BigDecimal mobility_grocery_and_pharmacy;
    private BigDecimal mobility_parks;
    private BigDecimal mobility_transit_stations;
    private BigDecimal mobility_workplaces;
    private BigDecimal mobility_residential;
    private BigDecimal school_closing;
    private BigDecimal workplace_closing;
    private BigDecimal cancel_public_events;
    private BigDecimal restrictions_on_gatherings;
    private BigDecimal public_transport_closing;
    private BigDecimal stay_at_home_requirements;
    private BigDecimal restrictions_on_internal_movement;
    private BigDecimal international_travel_controls;
    private BigDecimal income_support;
    private BigDecimal debt_relief;
    private BigDecimal fiscal_measures;
    private BigDecimal international_support;
    private BigDecimal public_information_campaigns;
    private BigDecimal testing_policy;
    private BigDecimal contact_tracing;
    private BigDecimal emergency_investment_in_healthcare;
    private BigDecimal investment_in_vaccines;
    private BigDecimal facial_coverings;
    private BigDecimal vaccination_policy;
    private BigDecimal stringency_index;
    private BigDecimal average_temperature_celsius;
    private BigDecimal minimum_temperature_celsius;
    private BigDecimal maximum_temperature_celsius;
    private BigDecimal rainfall_mm;
    private BigDecimal snowfall_mm;
    private BigDecimal dew_point;
    private BigDecimal relative_humidity;
    private BigDecimal aggregation_level;
}
