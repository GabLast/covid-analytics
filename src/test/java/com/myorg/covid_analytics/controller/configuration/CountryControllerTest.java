package com.myorg.covid_analytics.controller.configuration;

import com.myorg.covid_analytics.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CountryControllerTest extends AbstractControllerTest {

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/country/findall"))
                .andExpect(status().isOk());
    }
}
