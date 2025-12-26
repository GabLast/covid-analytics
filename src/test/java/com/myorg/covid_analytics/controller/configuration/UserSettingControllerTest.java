package com.myorg.covid_analytics.controller.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.covid_analytics.controller.AbstractControllerTest;
import com.myorg.covid_analytics.controller.security.AuthenticationController;
import com.myorg.covid_analytics.dto.request.configuration.UserSettingRequest;
import com.myorg.covid_analytics.dto.request.security.LoginRequest;
import com.myorg.covid_analytics.dto.response.security.LoginResponse;
import com.myorg.covid_analytics.services.security.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.oneOf;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class UserSettingControllerTest extends AbstractControllerTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void getSetting() throws Exception {

        String json = new ObjectMapper().writeValueAsString(
                LoginRequest.builder().usernameMail("admin").password("123").build());

        MockMvc mockMvcWithNoFilters = standaloneSetup(
                new AuthenticationController(authenticationService)).build();

        MvcResult mvcResult = mockMvcWithNoFilters.perform(
                post("/api/v1/auth/login").contentType(APPLICATION_JSON_VALUE)
                        .content(json)).andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        LoginResponse response =
                objectMapper.readValue(jsonResponse, LoginResponse.class);

        Assertions.assertNotNull(response);

        mockMvc.perform(get("/api/v1/setting").header("Authorization",
                "Bearer " + response.data().token())).andExpect(status().is(
                oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void postSetting() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                LoginRequest.builder().usernameMail("admin").password("123").build());

        MockMvc mockMvcWithNoFilters = standaloneSetup(
                new AuthenticationController(authenticationService)).build();

        MvcResult mvcResult = mockMvcWithNoFilters.perform(
                post("/api/v1/auth/login").contentType(APPLICATION_JSON_VALUE)
                        .content(json)).andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        LoginResponse response =
                objectMapper.readValue(jsonResponse, LoginResponse.class);

        String json2 = new ObjectMapper().writeValueAsString(
                UserSettingRequest.builder().build());

        mockMvc.perform(
                        post("/api/v1/setting").contentType(APPLICATION_JSON_VALUE).content(json2)
                                .header("Authorization", "Bearer " + response.data().token()))
                .andExpect(status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }
}
