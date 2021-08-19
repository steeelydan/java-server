package com.steeelydan.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {
    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>My Site</title></head><body><h1>This page was created using a simple Java Server</h1></body></html>";

            final String CRLF = "\r\n"; // 13, 10

            String response = "HTTP/1.1 200 OK" + CRLF + // Status line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF + CRLF + // Headers
                    html + CRLF + CRLF;

            byte[] responseInBytes = response.getBytes();

            outputStream.write(responseInBytes);

            inputStream.close();
            outputStream.close();
            socket.close();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Processing finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
