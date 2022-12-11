package org.client.services;

import org.classes.LoginForm;
import org.classes.Request;
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

    public void logOut() {
        if (isLoggedIn()) {
            // Making logOut request
        }

        currentUser = null;
    }
}
