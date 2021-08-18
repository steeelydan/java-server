package com.steeelydan.server.config;

public class HttpConfigurationException extends RuntimeException {
    public HttpConfigurationException() {
    }

    public HttpConfigurationException(String errorMessage) {
        super(errorMessage);
    }

    public HttpConfigurationException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public HttpConfigurationException(Throwable cause) {
        super(cause);
    }
}
