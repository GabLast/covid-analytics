package com.myorg.covid_analytics.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.covid_analytics.dto.response.GenericResponse;
import com.myorg.covid_analytics.dto.response.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public abstract class FilterErrorHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    void handleError(HttpServletRequest request, HttpServletResponse response, int status,
                     String message) {
        response.setStatus(status);
        response.setContentType("application/json");
        GenericResponse genericResponse = GenericResponse.builder()
                .responseInfo(ResponseInfo.builder().message(message)
                        .path(request.getRequestURI())
                        .status(status).build())
                .build();

        byte[] responseBody = null;
        try {
            responseBody = objectMapper.writeValueAsBytes(genericResponse);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert error message to json string: ", e);
        }

        try {
            if (responseBody != null && !response.isCommitted()) {
                response.getOutputStream().write(responseBody);
                response.flushBuffer();
            }
        } catch (IOException e) {
            log.error("Unable to write message to the response: ", e);
        }
    }
}
