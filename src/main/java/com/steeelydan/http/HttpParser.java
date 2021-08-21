package com.steeelydan.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10

    // Not static: design choice; usable in every thread with different config!
    public HttpRequest parseHttpRequest(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII); // byte stream -> char
                                                                                                  // stream

        HttpRequest request = new HttpRequest();
        parseRequestLine(reader, request);
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
        int readerChar;
        while ((readerChar = reader.read()) >= 0) {
            if (readerChar == CR) {
                readerChar = reader.read();
                if (readerChar == LF) {
                    return;
                }
            }
        }
    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request) {
    }

}
