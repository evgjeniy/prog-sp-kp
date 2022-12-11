package org.server;

import org.server.daos.UserDao;
import org.server.db_connection.DbSessionFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStartup {
    public static final UserDao userDao = new UserDao();

    public static void main(String[] port) {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port[0]));
            System.out.println("Сервер запущен!");
            while (true) {
                clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
        finally {
            try {
                if (clientSocket != null) clientSocket.close();
                System.out.println("Сервер остановлен");
                if (serverSocket != null) serverSocket.close();
            } catch (IOException ex) { ex.printStackTrace(); }
        }

        DbSessionFactory.close();
    }
}

