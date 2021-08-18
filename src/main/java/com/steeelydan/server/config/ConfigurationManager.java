package com.steeelydan.server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.steeelydan.server.util.Json;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;

    private ConfigurationManager() {
    }

    // Singleton pattern
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }

        return configurationManager;
    }

    private void closeFile(FileReader fileReader) {
        try {
            fileReader.close(); // Not in tutorial
        } catch (IOException e) {
            throw new HttpConfigurationException("Error at closing the file", e);
        }
    }

    public void loadConfigurationFile(String filePath) {
        FileReader fileReader;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer stringBuffer = new StringBuffer();

        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                stringBuffer.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode configuration;
        try {
            configuration = Json.parse(stringBuffer.toString());
        } catch (IOException e) {
            closeFile(fileReader);
            throw new HttpConfigurationException("Error parsing the configuration file", e);
        }

        try {
            currentConfiguration = Json.fromJson(configuration, Configuration.class);
        } catch (JsonProcessingException e) {
            closeFile(fileReader);
            throw new HttpConfigurationException("Error parsing the configuration file, internal", e);
        }

        closeFile(fileReader);
    }

    public Configuration getCurrentConfiguration() {
        if (currentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set.");
        } else {
            return currentConfiguration;
        }
    }
}
