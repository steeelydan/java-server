package com.steeelydan.server;

import java.io.IOException;

import com.steeelydan.server.config.Configuration;
import com.steeelydan.server.config.ConfigurationManager;
import com.steeelydan.server.core.ServerListenerThread;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Driver class for the HTTP Server
 */
public class Server {

    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        LOGGER.info("Server starting");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + configuration.getPort());
        LOGGER.info("Webroot: " + configuration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch(IOException e) {
            LOGGER.error("Error creating ServerListenerThread", e);
        }
    }
}
