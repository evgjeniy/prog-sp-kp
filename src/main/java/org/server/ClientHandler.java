package org.server;

import org.classes.Request;
import org.server.services.ServerRequestBinder;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) { this.clientSocket = clientSocket; }

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
        } catch (SocketCloseException ex) {
            try { clientSocket.close(); }
            catch (IOException e) { throw new RuntimeException(e); }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static class SocketCloseException extends SocketException {}
}