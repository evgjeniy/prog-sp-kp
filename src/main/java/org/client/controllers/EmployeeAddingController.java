package org.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.classes.Request;
import org.client.services.ClientRequestBinder;
import org.server.models.Employee;
import org.server.models.Role;
import org.server.models.User;

import java.io.IOException;
import java.sql.Date;
import java.util.regex.Pattern;

public class EmployeeAddingController {
    public static EmployeeAddingController instance;
    public Label title;
    public TextField nameField;
    public TextField surnameField;
    public TextField birthdayField;
    public TextField postField;
    public TextField salaryField;
    public TextField loginField;
    public TextField passwordField;
    public TextField mailField;
    public Label errorMessage;
    public MenuButton roleButton;
    public Button submitButton;

    public Role role;

    @FXML
    public void initialize() {
        instance = this;
    }

    private User getUserFromFields() {
        User user = new User(loginField.getText(), passwordField.getText());
        Employee employee = new Employee(nameField.getText(), surnameField.getText(),
                Date.valueOf(birthdayField.getText()), postField.getText(),
                Float.parseFloat(salaryField.getText()), mailField.getText());
        employee.setUser(user);
        user.setRole(role);
        user.setEmployee(employee);
        return user;
    }

    public void setRole(ActionEvent actionEvent) {
        switch (((MenuItem)actionEvent.getSource()).getText()) {
            case "Сотрудник" -> {
                role = new Role(1, "User");
                roleButton.setText("Сотрудник");
            }
            case "Администратор" -> {
                role = new Role(2, "Admin");
                roleButton.setText("Администратор");
            }
            case "Менеджер" -> {
                role = new Role(3, "Manager");
                roleButton.setText("Менеджер");
            }
        }
    }

    public void setAddingMode() {
        title.setText("Добавление нового сотрудника");
        nameField.setText("");
        surnameField.setText("");
        birthdayField.setText("");
        postField.setText("");
        salaryField.setText("");
        loginField.setText("");
        passwordField.setText("");
        mailField.setText("");
        errorMessage.setText("");
        roleButton.setText("Уровень доступа");
        role = null;
        submitButton.setText("Добавить");
        submitButton.setOnAction(e -> {
            try {
                if (!validateUserProperties(true)) return;

                setAddingMode();
                ClientRequestBinder.instance.make(Request.insertUser, getUserFromFields());
                if (ClientRequestBinder.instance.receive()) EmployeesController.instance.reloadPage();
            } catch (IOException | ClassNotFoundException ignored) {}
        });
    }

    public void setEditingMode() {
        User user = EmployeeDetailsController.instance.currentUser;

        title.setText("Редактирование данных сотрудника");
        nameField.setText(user.getEmployee().getName());
        surnameField.setText(user.getEmployee().getSurname());
        birthdayField.setText(user.getEmployee().getBirthday().toString());
        postField.setText(user.getEmployee().getPost());
        salaryField.setText(user.getEmployee().getSalary() + "");
        role = null;
        roleButton.setText("Уровень доступа: ");
        loginField.setText(user.getLogin());
        passwordField.setText("");
        mailField.setText(user.getEmployee().getMail());
        submitButton.setText("Сохранить");
        submitButton.setOnAction(e -> {
            try {
                if (!validateUserProperties(false)) return;

                User userToUpdate = getUserFromFields();
                userToUpdate.setId(user.getId());
                setAddingMode();
                ClientRequestBinder.instance.make(Request.updateUser, userToUpdate);
                if (ClientRequestBinder.instance.receive()) EmployeesController.instance.reloadPage();
            } catch (IOException | ClassNotFoundException ignored) {}
        });
    }

    private boolean validateUserProperties(boolean withLoginExistingCheck) throws IOException, ClassNotFoundException {
        Pattern namePattern = Pattern.compile("^[A-ZА-Я][a-zа-я]+$");
        if (!namePattern.matcher(nameField.getText()).matches()) {
            errorMessage.setText("Неверный формат имени");
            return false;
        }
        if (!namePattern.matcher(surnameField.getText()).matches()) {
            errorMessage.setText("Неверный формат фамилии");
            return false;
        }
        if (!Pattern.compile("^(19|20)?[0-9]{2}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$").
                matcher(birthdayField.getText()).matches()) {
            errorMessage.setText("Неверный формат даты");
            return false;
        }
        if (!Pattern.compile("^[[A-ZА-Я][a-zа-я]+ ?]+$").matcher(postField.getText()).matches()) {
            errorMessage.setText("Неверный формат должности");
            return false;
        }
        if (!Pattern.compile("^[0-9]+.[0-9]{2}$").matcher(salaryField.getText()).matches()) {
            errorMessage.setText("Неверный формат зарплаты");
            return false;
        }
        if (role == null) {
            errorMessage.setText("Не выбран уровень доступа");
            return false;
        }
        if (!Pattern.compile("^[A-Za-z]{6,20}$").matcher(loginField.getText()).matches()) {
            errorMessage.setText("Неверный формат логина\n(от 6 до 20 лат. символов)");
            return false;
        } else {
            ClientRequestBinder.instance.make(Request.getUserByLogin, loginField.getText());
            User user = ClientRequestBinder.instance.receive();
            if (user != null && user.getId() != EmployeeDetailsController.instance.currentUser.getId()) {
                errorMessage.setText("Сотрудник с таким\nлогином уже существует");
                return false;
            }
        }
        if (!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$").
                matcher(passwordField.getText()).matches()) {
            errorMessage.setText("Пароль должен быть размером [8;20]\nСодержать символы: [0-9],[a-z],[A-Z]");
            return false;
        }
        if (!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(mailField.getText()).matches()) {
            errorMessage.setText("Неверный формат EMail");
            return false;
        }
        return true;
    }
}
