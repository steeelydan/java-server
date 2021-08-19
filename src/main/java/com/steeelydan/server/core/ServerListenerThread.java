package com.steeelydan.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread extends Thread {
    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
            // serverSocket.close(); // TODO handle later
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
