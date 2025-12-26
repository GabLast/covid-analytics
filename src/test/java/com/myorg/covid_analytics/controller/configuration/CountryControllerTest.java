package com.myorg.covid_analytics.controller.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.covid_analytics.controller.AbstractControllerTest;
import com.myorg.covid_analytics.dto.request.security.ProfileRequest;
import com.myorg.covid_analytics.services.security.AuthenticationService;
import com.myorg.covid_analytics.services.security.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.oneOf;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class CountryControllerTest extends AbstractControllerTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService           userService;

    @Test
    void testFindAll() throws Exception {
        String token = authenticationService.generateJWT(
                authenticationService.generateToken(
                        userService.findByUsernameOrMail("admin")));

        Assertions.assertNotNull(token);

        mockMvc.perform(
                        get("/api/v1/country/fetch").header("Authorization", "Bearer " + token))
                .andExpect(status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void postTest() throws Exception {
        //        String json = new ObjectMapper().writeValueAsString(
        //                LoginRequest.builder().usernameMail("admin").password("123").build());
        //
        //        MockMvc mockMvcWithNoFilters = standaloneSetup(
        //                new AuthenticationController(authenticationService)).build();
        //
        //        MvcResult mvcResult = mockMvcWithNoFilters.perform(
        //                post("/api/v1/auth/login").contentType(APPLICATION_JSON_VALUE)
        //                        .content(json)).andExpect(status().isOk()).andReturn();
        //
        //        String jsonResponse = mvcResult.getResponse().getContentAsString();
        //        LoginResponse response =
        //                objectMapper.readValue(jsonResponse, LoginResponse.class);
        //
        //        Assertions.assertNotNull(response);

        String token = authenticationService.generateJWT(
                authenticationService.generateToken(
                        userService.findByUsernameOrMail("admin")));

        Assertions.assertNotNull(token);

        String json2 = new ObjectMapper().writeValueAsString(
                ProfileRequest.builder().name("Test profile").build());

        mockMvc.perform(
                post("/api/v1/country").contentType(APPLICATION_JSON_VALUE).content(json2)
                        .header("Authorization", "Bearer " + token)).andExpect(
                status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void getTest() throws Exception {
        String token = authenticationService.generateJWT(
                authenticationService.generateToken(
                        userService.findByUsernameOrMail("admin")));

        Assertions.assertNotNull(token);

        mockMvc.perform(get("/api/v1/country" + "?id=" + 1).header("Authorization",
                "Bearer " + token)).andExpect(status().is(
                oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void findall() throws Exception {
        String token = authenticationService.generateJWT(
                authenticationService.generateToken(
                        userService.findByUsernameOrMail("admin")));

        Assertions.assertNotNull(token);

        mockMvc.perform(
                        get("/api/v1/country/findall").header("Authorization", "Bearer " + token))
                .andExpect(status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void countall() throws Exception {
        String token = authenticationService.generateJWT(
                authenticationService.generateToken(
                        userService.findByUsernameOrMail("admin")));

        Assertions.assertNotNull(token);

        mockMvc.perform(get("/api/v1/country/countall").header("Authorization",
                "Bearer " + token)).andExpect(status().is(
                oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }
}
