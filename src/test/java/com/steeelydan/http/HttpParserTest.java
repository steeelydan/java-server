package com.steeelydan.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class HttpParserTest {
    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request;
		try {
			request = httpParser.parseHttpRequest(generateValidGETTestCase());
            assertEquals(request.getMethod(), HttpMethod.GET);
		} catch (HttpParsingException e) {
			fail();
		}

    }

    @Test
    void parseHttpRequestBadMethod() {
        try {
			httpParser.parseHttpRequest(generateInvalidGETTestCase());
			fail();
		} catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
		}
    }

    @Test
    void parseHttpRequestTooLongMethod() {
        try {
            httpParser.parseHttpRequest(generateTooLongMethodGETTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestTooManyItems() {
        try {
            httpParser.parseHttpRequest(generateTooManyItemsGETTestCase());
            fail();
        } catch (HttpParsingException e) {
            e.printStackTrace();
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequestEmptyRequestLine() {
        try {
            httpParser.parseHttpRequest(generateEmptyRequestLineGETTestCase());
            fail();
        } catch (HttpParsingException e) {
            e.printStackTrace();
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequestNoLineFeed() {
        try {
            httpParser.parseHttpRequest(generateNoLineFeedGETTestCase());
            fail();
        } catch (HttpParsingException e) {
            e.printStackTrace();
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidGETTestCase() {
        String rawData = "GET / HTTP/1.1\r\n" + "Host: localhost:8080\r\n"
                + "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0\r\n"
                + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n"
                + "Accept-Language: en-US,en;q=0.5\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "DNT: 1\r\n"
                + "Connection: keep-alive\r\n" + "Upgrade-Insecure-Requests: 1\r\n" + "Sec-Fetch-Dest: document\r\n"
                + "Sec-Fetch-Mode: navigate\r\n" + "Sec-Fetch-Site: none\r\n" + "Sec-Fetch-User: ?1\r\n"
                + "Cache-Control: max-age=0\r\n" + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateInvalidGETTestCase() {
        String rawData = "GeT / HTTP/1.1\r\n" + "Host: localhost:8080\r\n"
                + "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0\r\n"
                + "Cache-Control: max-age=0\r\n" + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateTooLongMethodGETTestCase() {
        String rawData = "GETTTTT / HTTP/1.1\r\n" + "Host: localhost:8080\r\n"
                + "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0\r\n"
                + "Cache-Control: max-age=0\r\n" + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateTooManyItemsGETTestCase() {
        String rawData = "GET / AAAAAAAA HTTP/1.1\r\n" + "Host: localhost:8080\r\n"
                + "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0\r\n"
                + "Cache-Control: max-age=0\r\n" + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateEmptyRequestLineGETTestCase() {
        String rawData = "\r\n" + "Host: localhost:8080\r\n"
                + "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0\r\n"
                + "Cache-Control: max-age=0\r\n" + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }

    private InputStream generateNoLineFeedGETTestCase() {
        String rawData = "GET / HTTP/1.1\r" + "Host: localhost:8080\r\n"
                + "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0\r\n"
                + "Cache-Control: max-age=0\r\n" + "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }
}
