package com.myorg.covid_analytics.exceptions;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }
}
