package com.myorg.covid_analytics.controller.security;

import com.myorg.covid_analytics.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PermitControllerTest extends AbstractControllerTest {

    @Test
    void fetch() throws Exception {
        mockMvc.perform(get("/api/v1/permits/fetch"))
                .andExpect(status().isOk());
    }

}
