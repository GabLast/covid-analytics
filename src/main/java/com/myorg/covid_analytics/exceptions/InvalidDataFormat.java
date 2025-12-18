package com.myorg.covid_analytics.exceptions;

public class InvalidDataFormat extends RuntimeException {
    public InvalidDataFormat(String message) {
        super(message);
    }
}
