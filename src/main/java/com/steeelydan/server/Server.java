package com.steeelydan.server;

import com.steeelydan.server.config.Configuration;
import com.steeelydan.server.config.ConfigurationManager;

/**
 * Driver class for the HTTP Server
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("Server starting");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port: " + configuration.getPort());
        System.out.println("Webroot: " + configuration.getWebroot());
    }
}
