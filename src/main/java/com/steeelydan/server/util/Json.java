package com.steeelydan.server.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json {
    private static ObjectMapper objectMapper = createDefaultObjectMapper();

    public static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return objectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> classToBeConvertedTo) throws JsonProcessingException {
        return objectMapper.treeToValue(node, classToBeConvertedTo);
    }

    public static JsonNode toJson(Object object) {
        return objectMapper.valueToTree(object);
    }

    public static String printJson(JsonNode jsonNode) throws JsonProcessingException {
        return generateJson(jsonNode, false);
    }

    public static String printJsonPretty(JsonNode jsonNode) throws JsonProcessingException {
        return generateJson(jsonNode, true);
    }

    private static String generateJson(Object object, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();

        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return objectWriter.writeValueAsString(object);
    }
}
