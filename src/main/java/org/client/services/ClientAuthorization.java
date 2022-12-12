package org.client.services;

import org.classes.Request;
import org.client.ClientStartup;
import org.server.models.User;

import java.io.IOException;

public class ClientAuthorization {
    private User currentUser;

    public ClientAuthorization() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getUser() {
        return currentUser;
    }

    public boolean logIn(String login, String password) throws IOException, ClassNotFoundException {
        ClientRequestBinder.instance.make(Request.login, login + ";" + password);
        currentUser = ClientRequestBinder.instance.receive();

        return currentUser != null;
    }

    public void logOut() throws IOException {
        if (isLoggedIn()) { currentUser = null; }
    }
}
