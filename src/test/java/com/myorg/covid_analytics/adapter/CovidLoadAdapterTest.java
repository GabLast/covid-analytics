package com.myorg.covid_analytics.adapter;

import com.myorg.covid_analytics.dao.CovidRowCsv;
import com.myorg.covid_analytics.repositories.configuration.CountryRepository;
import com.myorg.covid_analytics.services.configuration.CountryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CovidLoadAdapterTest {

    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryService service;

    @Test
    void csvDatasetToModel() {
        Assertions.assertNotNull(
                CovidLoadAdapter.csvDatasetToModel(new CovidRowCsv(), null, null));
    }

    @Test
    void jsonDatasetToModel() {
        Assertions.assertNotNull(
                CovidLoadAdapter.csvDatasetToModel(new CovidRowCsv(), null, null));
    }
}
