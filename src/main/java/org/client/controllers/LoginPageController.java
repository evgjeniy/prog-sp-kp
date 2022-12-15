package org.client.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.client.ClientStartup;

import java.io.IOException;
public class LoginPageController {
    public TextField loginField;
    public PasswordField passwordField;

    public void login(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || password.isBlank()) return;

        boolean result = ClientStartup.clientAuthorization.logIn(login, password);

        if (!result) {
            loginField.setText("Имя / пароль введены не верно");
            passwordField.clear();
        } else {
            ClientStartup.loadPage("main.fxml");
        }
    }
}
