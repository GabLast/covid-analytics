package com.myorg.covid_analytics.adapter;

import com.myorg.covid_analytics.dao.CovidDataSet;
import com.myorg.covid_analytics.dao.CovidRowCsv;
import com.myorg.covid_analytics.models.configurations.Country;
import com.myorg.covid_analytics.models.process.CovidLoadDetail;
import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import com.myorg.covid_analytics.services.configuration.CountryService;
import com.myorg.covid_analytics.utils.EtlUtilities;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CovidLoadAdapter {

    public static CovidLoadDetail csvDatasetToModel(
            CovidRowCsv data,
            CovidLoadHeader header, Country country) {

        CovidLoadDetail detail = CovidLoadDetail.builder()
                .header(header)
                .country(country)
                .date(data.getDate())

        . aggregation_level(data.getAggregation_level())
        . new_confirmed(data.getNew_confirmed())
        . new_deceased(data.getNew_deceased())
        . new_tested(data.getNew_tested())
        . new_persons_vaccinated(data.getNew_persons_vaccinated())
        . new_persons_fully_vaccinated(data.getNew_persons_fully_vaccinated())
        . new_vaccine_doses_administered(data.getNew_vaccine_doses_administered())
        . population(data.getPopulation())
        . population_male(data.getPopulation_male())
        . population_female(data.getPopulation_female())
        . population_rural(data.getPopulation_rural())
        . population_urban(data.getPopulation_urban())
        . population_largest_city(data.getPopulation_largest_city())
        . population_clustered(data.getPopulation_clustered())
        . population_density(data.getPopulation_density())
        . human_development_index(data.getHuman_development_index())
        . population_age_00_09(data.getPopulation_age_00_09())
        . population_age_10_19(data.getPopulation_age_10_19())
        . population_age_20_29(data.getPopulation_age_20_29())
        . population_age_30_39(data.getPopulation_age_30_39())
        . population_age_40_49(data.getPopulation_age_40_49())
        . population_age_50_59(data.getPopulation_age_50_59())
        . population_age_60_69(data.getPopulation_age_60_69())
        . population_age_70_79(data.getPopulation_age_70_79())
        . population_age_80_and_older(data.getPopulation_age_80_and_older())
        . gdp_usd(data.getGdp_usd())
        . gdp_per_capita_usd(data.getGdp_per_capita_usd())
        . human_capital_index(data.getHuman_capital_index())
        . openstreetmap_id(data.getOpenstreetmap_id())
        . latitude(data.getLatitude())
        . longitude(data.getLongitude())
        . area_sq_km(data.getArea_sq_km())
        . area_rural_sq_km(data.getArea_rural_sq_km())
        . area_urban_sq_km(data.getArea_urban_sq_km())
        . life_expectancy(data.getLife_expectancy())
        . smoking_prevalence(data.getSmoking_prevalence())
        . diabetes_prevalence(data.getDiabetes_prevalence())
        . infant_mortality_rate(data.getInfant_mortality_rate())
        . adult_male_mortality_rate(data.getAdult_male_mortality_rate())
        . adult_female_mortality_rate(data.getAdult_female_mortality_rate())
        . pollution_mortality_rate(data.getPollution_mortality_rate())
        . comorbidity_mortality_rate(data.getComorbidity_mortality_rate())
        . nurses_per_1000(data.getNurses_per_1000())
        . physicians_per_1000(data.getPhysicians_per_1000())
        . health_expenditure_usd(data.getHealth_expenditure_usd())
        . out_of_pocket_health_expenditure_usd(data.getOut_of_pocket_health_expenditure_usd())
        . mobility_retail_and_recreation(data.getMobility_retail_and_recreation())
        . mobility_grocery_and_pharmacy(data.getMobility_grocery_and_pharmacy())
        . mobility_parks(data.getMobility_parks())
        . mobility_transit_stations(data.getMobility_transit_stations())
        . mobility_workplaces(data.getWorkplace_closing())
        . mobility_residential(data.getMobility_residential())
        . school_closing(data.getSchool_closing())
        . workplace_closing(data.getWorkplace_closing())
        . cancel_public_events(data.getCancel_public_events())
        . restrictions_on_gatherings(data.getRestrictions_on_gatherings())
        . public_transport_closing(data.getPublic_transport_closing())
        . stay_at_home_requirements(data.getStay_at_home_requirements())
        . restrictions_on_internal_movement(data.getRestrictions_on_internal_movement())
        . international_travel_controls(data.getInternational_travel_controls())
        . income_support(data.getIncome_support())
        . debt_relief(data.getDebt_relief())
        . fiscal_measures(data.getFiscal_measures())
        . international_support(data.getInternational_support())
        . public_information_campaigns(data.getPublic_information_campaigns())
        . testing_policy(data.getTesting_policy())
        . contact_tracing(data.getContact_tracing())
        . emergency_investment_in_healthcare(data.getEmergency_investment_in_healthcare())
        . investment_in_vaccines(data.getInvestment_in_vaccines())
        . facial_coverings(data.getFacial_coverings())
        . vaccination_policy(data.getVaccination_policy())
        . stringency_index(data.getStringency_index())
        . average_temperature_celsius(data.getAverage_temperature_celsius())
        . minimum_temperature_celsius(data.getMinimum_temperature_celsius())
        . maximum_temperature_celsius(data.getMaximum_temperature_celsius())
        . rainfall_mm(data.getRainfall_mm())
        . snowfall_mm(data.getSnowfall_mm())
        . dew_point(data.getDew_point())
        . relative_humidity(data.getRelative_humidity())
        .build();

        return detail;
    }

    public static List<CovidLoadDetail> jsonDatasetToModel(
            CovidDataSet data,
            CovidLoadHeader header, CountryService countryService) {

        List<CovidLoadDetail> list = new ArrayList<>();

        if(CollectionUtils.isEmpty(data.data())) {
            return list;
        }

        for (int i = 0; i < data.data().size(); i++) {
            Map<String, Object> row = data.rowAsMap(i);

            CovidLoadDetail detail = CovidLoadDetail.builder()
                    .header(header)
                    .country(countryService.findByAnyIdentifier(true,
                            EtlUtilities.getStringValue(row, "country_code")).orElse(null))
                    .date(EtlUtilities.getLocalDateValue(row, "date"))
                    . aggregation_level(EtlUtilities.getBigDecimalValue(row, "aggregation_level"))
                    . new_confirmed(EtlUtilities.getBigDecimalValue(row, "new_confirmed"))
                    . new_deceased(EtlUtilities.getBigDecimalValue(row, "new_deceased"))
                    . new_tested(EtlUtilities.getBigDecimalValue(row, "new_tested"))
                    . new_persons_vaccinated(EtlUtilities.getBigDecimalValue(row, "new_persons_vaccinated"))
                    . new_persons_fully_vaccinated(EtlUtilities.getBigDecimalValue(row, "new_persons_fully_vaccinated"))
                    . new_vaccine_doses_administered(EtlUtilities.getBigDecimalValue(row, "new_vaccine_doses_administered"))
                    . population(EtlUtilities.getBigDecimalValue(row, "population"))
                    . population_male(EtlUtilities.getBigDecimalValue(row, "population_male"))
                    . population_female(EtlUtilities.getBigDecimalValue(row, "population_female"))
                    . population_rural(EtlUtilities.getBigDecimalValue(row, "population_rural"))
                    . population_urban(EtlUtilities.getBigDecimalValue(row, "population_urban"))
                    . population_largest_city(EtlUtilities.getBigDecimalValue(row, "population_largest_city"))
                    . population_clustered(EtlUtilities.getBigDecimalValue(row, "population_clustered"))
                    . population_density(EtlUtilities.getBigDecimalValue(row, "population_density"))
                    . human_development_index(EtlUtilities.getBigDecimalValue(row, "human_development_index"))
                    . population_age_00_09(EtlUtilities.getBigDecimalValue(row, "population_age_00_09"))
                    . population_age_10_19(EtlUtilities.getBigDecimalValue(row, "population_age_10_19"))
                    . population_age_20_29(EtlUtilities.getBigDecimalValue(row, "population_age_20_29"))
                    . population_age_30_39(EtlUtilities.getBigDecimalValue(row, "population_age_30_39"))
                    . population_age_40_49(EtlUtilities.getBigDecimalValue(row, "population_age_40_49"))
                    . population_age_50_59(EtlUtilities.getBigDecimalValue(row, "population_age_50_59"))
                    . population_age_60_69(EtlUtilities.getBigDecimalValue(row, "population_age_60_69"))
                    . population_age_70_79(EtlUtilities.getBigDecimalValue(row, "population_age_70_79"))
                    . population_age_80_and_older(EtlUtilities.getBigDecimalValue(row, "population_age_80_and_older"))
                    . gdp_usd(EtlUtilities.getBigDecimalValue(row, "gdp_usd"))
                    . gdp_per_capita_usd(EtlUtilities.getBigDecimalValue(row, "gdp_per_capita_usd"))
                    . human_capital_index(EtlUtilities.getBigDecimalValue(row, "human_capital_index"))
                    . openstreetmap_id(EtlUtilities.getLongValue(row, "openstreetmap_id"))
                    . latitude(EtlUtilities.getBigDecimalValue(row, "latitude"))
                    . longitude(EtlUtilities.getBigDecimalValue(row, "longitude"))
                    . area_sq_km(EtlUtilities.getBigDecimalValue(row, "area_sq_km"))
                    . area_rural_sq_km(EtlUtilities.getBigDecimalValue(row, "area_rural_sq_km"))
                    . area_urban_sq_km(EtlUtilities.getBigDecimalValue(row, "area_urban_sq_km"))
                    . life_expectancy(EtlUtilities.getBigDecimalValue(row, "life_expectancy"))
                    . smoking_prevalence(EtlUtilities.getBigDecimalValue(row, "smoking_prevalence"))
                    . diabetes_prevalence(EtlUtilities.getBigDecimalValue(row, "diabetes_prevalence"))
                    . infant_mortality_rate(EtlUtilities.getBigDecimalValue(row, "infant_mortality_rate"))
                    . adult_male_mortality_rate(EtlUtilities.getBigDecimalValue(row, "adult_male_mortality_rate"))
                    . adult_female_mortality_rate(EtlUtilities.getBigDecimalValue(row, "adult_female_mortality_rate"))
                    . pollution_mortality_rate(EtlUtilities.getBigDecimalValue(row, "pollution_mortality_rate"))
                    . comorbidity_mortality_rate(EtlUtilities.getBigDecimalValue(row, "comorbidity_mortality_rate"))
                    . nurses_per_1000(EtlUtilities.getBigDecimalValue(row, "nurses_per_1000"))
                    . physicians_per_1000(EtlUtilities.getBigDecimalValue(row, "physicians_per_1000"))
                    . health_expenditure_usd(EtlUtilities.getBigDecimalValue(row, "health_expenditure_usd"))
                    . out_of_pocket_health_expenditure_usd(EtlUtilities.getBigDecimalValue(row, "out_of_pocket_health_expenditure_usd"))
                    . mobility_retail_and_recreation(EtlUtilities.getBigDecimalValue(row, "mobility_retail_and_recreation"))
                    . mobility_grocery_and_pharmacy(EtlUtilities.getBigDecimalValue(row, "mobility_grocery_and_pharmacy"))
                    . mobility_parks(EtlUtilities.getBigDecimalValue(row, "mobility_parks"))
                    . mobility_transit_stations(EtlUtilities.getBigDecimalValue(row, "mobility_transit_stations"))
                    . mobility_workplaces(EtlUtilities.getBigDecimalValue(row, "mobility_workplaces"))
                    . mobility_residential(EtlUtilities.getBigDecimalValue(row, "mobility_residential"))
                    . school_closing(EtlUtilities.getBigDecimalValue(row, "school_closing"))
                    . workplace_closing(EtlUtilities.getBigDecimalValue(row, "workplace_closing"))
                    . cancel_public_events(EtlUtilities.getBigDecimalValue(row, "cancel_public_events"))
                    . restrictions_on_gatherings(EtlUtilities.getBigDecimalValue(row, "restrictions_on_gatherings"))
                    . public_transport_closing(EtlUtilities.getBigDecimalValue(row, "public_transport_closing"))
                    . stay_at_home_requirements(EtlUtilities.getBigDecimalValue(row, "stay_at_home_requirements"))
                    . restrictions_on_internal_movement(EtlUtilities.getBigDecimalValue(row, "restrictions_on_internal_movement"))
                    . international_travel_controls(EtlUtilities.getBigDecimalValue(row, "international_travel_controls"))
                    . income_support(EtlUtilities.getBigDecimalValue(row, "income_support"))
                    . debt_relief(EtlUtilities.getBigDecimalValue(row, "debt_relief"))
                    . fiscal_measures(EtlUtilities.getBigDecimalValue(row, "fiscal_measures"))
                    . international_support(EtlUtilities.getBigDecimalValue(row, "international_support"))
                    . public_information_campaigns(EtlUtilities.getBigDecimalValue(row, "public_information_campaigns"))
                    . testing_policy(EtlUtilities.getBigDecimalValue(row, "testing_policy"))
                    . contact_tracing(EtlUtilities.getBigDecimalValue(row, "contact_tracing"))
                    . emergency_investment_in_healthcare(EtlUtilities.getBigDecimalValue(row, "emergency_investment_in_healthcare"))
                    . investment_in_vaccines(EtlUtilities.getBigDecimalValue(row, "investment_in_vaccines"))
                    . facial_coverings(EtlUtilities.getBigDecimalValue(row, "facial_coverings"))
                    . vaccination_policy(EtlUtilities.getBigDecimalValue(row, "vaccination_policy"))
                    . stringency_index(EtlUtilities.getBigDecimalValue(row, "stringency_index"))
                    . average_temperature_celsius(EtlUtilities.getBigDecimalValue(row, "average_temperature_celsius"))
                    . minimum_temperature_celsius(EtlUtilities.getBigDecimalValue(row, "minimum_temperature_celsius"))
                    . maximum_temperature_celsius(EtlUtilities.getBigDecimalValue(row, "maximum_temperature_celsius"))
                    . rainfall_mm(EtlUtilities.getBigDecimalValue(row, "rainfall_mm"))
                    . snowfall_mm(EtlUtilities.getBigDecimalValue(row, "snowfall_mm"))
                    . dew_point(EtlUtilities.getBigDecimalValue(row, "dew_point"))
                    . relative_humidity(EtlUtilities.getBigDecimalValue(row, "relative_humidity"))
                    .build();

            list.add(detail);
        }


        return list;
    }
}
