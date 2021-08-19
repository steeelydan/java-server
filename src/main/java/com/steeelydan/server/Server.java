package com.steeelydan.server;

import java.io.IOException;

import com.steeelydan.server.config.Configuration;
import com.steeelydan.server.config.ConfigurationManager;
import com.steeelydan.server.core.ServerListenerThread;

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

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch(IOException e) {
            e.printStackTrace();
            // TODO
        }
    }
}
