package org.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.classes.Request;
import org.client.ClientStartup;
import org.client.services.ClientRequestBinder;
import org.client.services.Validator;
import org.server.models.User;

import java.io.IOException;
import java.sql.Date;

public class PersonalAccountPageController {
    public static PersonalAccountPageController instance;
    public Label title;
    public TextField nameField;
    public TextField surnameField;
    public TextField birthdayField;
    public TextField loginField;
    public TextField passwordField;
    public TextField mailField;
    public Label salaryLabel;
    public Label postLabel;
    public Button saveButton;
    public Button exitButton;
    public Label errorMessage;

    @FXML
    private void initialize() {
        instance = this;

        saveButton.setOnAction(this::saveUserData);
        exitButton.setOnAction(this::logout);
        reloadUserPage(ClientStartup.clientAuthorization.getUser());
    }

    private void reloadUserPage(User user) {
        title.setText("Личный кабинет (" + user.getFullName() + ")");
        nameField.setText(user.getEmployee().getName());
        surnameField.setText(user.getEmployee().getSurname());
        birthdayField.setText(user.getEmployee().getBirthday().toString());
        loginField.setText(user.getLogin());
        passwordField.setText("");
        mailField.setText(user.getEmployee().getMail());
        salaryLabel.setText(user.getEmployee().getSalary() + " BYN");
        postLabel.setText(user.getEmployee().getPost());
    }

    private void saveUserData(ActionEvent actionEvent) {
        try {
            if (!validateUserProperties()) return;

            errorMessage.setText("");

            User user = ClientStartup.clientAuthorization.getUser();
            user.getEmployee().setName(nameField.getText());
            user.getEmployee().setSurname(surnameField.getText());
            user.getEmployee().setBirthday(Date.valueOf(birthdayField.getText()));
            user.setLogin(loginField.getText());
            user.setPassword(passwordField.getText());
            user.getEmployee().setMail(mailField.getText());

            ClientStartup.requestMapper.make(Request.updateUser, user);
            if (ClientStartup.requestMapper.receive()) {
                ClientStartup.clientAuthorization.logIn(user.getLogin(), user.getPassword());
                reloadUserPage(user);
            }

        } catch (Exception ignored) {}
    }

    private boolean validateUserProperties() throws IOException, ClassNotFoundException {
        if (!Validator.name(nameField.getText())) {
            errorMessage.setText("Неверный формат имени");
            return false;
        }
        if (!Validator.name(surnameField.getText())) {
            errorMessage.setText("Неверный формат фамилии");
            return false;
        }
        if (!Validator.sqlDate(birthdayField.getText())) {
            errorMessage.setText("Неверный формат даты");
            return false;
        }
        if (!Validator.login(loginField.getText())) {
            errorMessage.setText("Неверный формат логина\n(от 6 до 20 лат. символов)");
            return false;
        } else {
            ClientRequestBinder.instance.make(Request.getUserByLogin, loginField.getText());
            User user = ClientRequestBinder.instance.receive();
            if (user != null && user.getId() != ClientStartup.clientAuthorization.getUser().getId()) {
                errorMessage.setText("Сотрудник с таким\nлогином уже существует");
                return false;
            }
        }
        if (!Validator.password(passwordField.getText())) {
            errorMessage.setText("Пароль должен быть размером [8;20]\nСодержать символы: [0-9],[a-z],[A-Z]");
            return false;
        }
        if (!Validator.mail(mailField.getText())) {
            errorMessage.setText("Неверный формат EMail");
            return false;
        }
        return true;
    }

    private void logout(ActionEvent actionEvent) {
        try {
            ClientStartup.loadPage("login.fxml");
            ClientStartup.clientAuthorization.logOut();
        } catch (IOException ignored) {}
    }
}
