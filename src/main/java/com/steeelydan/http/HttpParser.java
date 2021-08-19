package com.steeelydan.http;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // Not static: design choice; usable in every thread with different config!
    public void parseHttpRequest(InputStream inputStream) {

    }

}
