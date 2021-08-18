package com.steeelydan.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

        try {
            ServerSocket serverSocket = new ServerSocket(configuration.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>My Site</title></head><body><h1>This page was created using a simple Java Server</h1></body></html>";

            final String CRLF = "\r\n"; // 13, 10


            String response =
            "HTTP/1.1 200 OK" + CRLF + // Status line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
            "Content-Length: " + html.getBytes().length + CRLF + CRLF + // Headers
            html +
            CRLF + CRLF;

            byte[] responseInBytes = response.getBytes();

            System.out.println(response);

            outputStream.write(responseInBytes);

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
