package org.server;

import org.classes.Request;
import org.server.services.ServerRequestBinder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ServerRequestBinder requestMapper = new ServerRequestBinder();

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            requestMapper.configure(in, out);

            while (!clientSocket.isClosed()) {
                Request requestType = requestMapper.receive();
                requestMapper.map(requestType);
            }

            clientSocket.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}