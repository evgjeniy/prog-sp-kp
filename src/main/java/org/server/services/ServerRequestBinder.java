package org.server.services;

import org.classes.LoginForm;
import org.classes.Request;
import org.server.ServerStartup;
import org.server.daos.UserDao;
import org.server.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServerRequestBinder {
    public static ServerRequestBinder instance;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ServerRequestBinder() {
        instance = this;
    }

    public void map(Request request) throws IOException, ClassNotFoundException {
        switch (request) {
            case login -> {
                String[] form = ((String)inputStream.readObject()).split(";");
                outputStream.writeObject(ServerAuthorization.login(form[0], form[1]));
            }
            case getUserById -> {
                int id = (int) inputStream.readObject();
                outputStream.writeObject(ServerStartup.userDao.get(id));
            }
            case getAllClients -> {
                outputStream.writeObject(new ArrayList<>(ServerStartup.userDao.get()));
            }
            case getUserByLogin -> {
                String login = (String) inputStream.readObject();
                outputStream.writeObject(ServerStartup.userDao.getByLogin(login));
            }
            case insertUser -> {
                User user = (User) inputStream.readObject();
                outputStream.writeObject(ServerStartup.userDao.insert(user));
            }
            case deleteById -> {
                int id = (int) inputStream.readObject();
                outputStream.writeObject(ServerStartup.userDao.delete(id));
            }
            case updateUser -> {
                User user = (User) inputStream.readObject();
                outputStream.writeObject(ServerStartup.userDao.update(user));
            }
        }
    }

    public <T> T receive() throws IOException, ClassNotFoundException {
        return (T) inputStream.readObject();
    }

    public void configure(ObjectInputStream in, ObjectOutputStream out) {
        this.inputStream = in;
        this.outputStream = out;
    }
}
