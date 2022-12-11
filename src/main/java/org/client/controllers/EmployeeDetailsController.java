package org.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.classes.Request;
import org.client.ClientStartup;
import org.client.services.ClientRequestBinder;
import org.server.models.User;

import java.io.IOException;

public class EmployeeDetailsController {
    public static EmployeeDetailsController instance;
    public Label detailName;
    public Label detailSurname;
    public Label detailBirthday;
    public Label detailPost;
    public Label detailSalary;
    public Label detailRole;
    public Label detailLogin;
    public Label detailMail;
    public Button editButton;
    public Button fireButton;

    public User currentUser;

    @FXML
    private void initialize() {
        instance = this;

        editButton.setOnAction(this::showEditPane);
        fireButton.setOnAction(this::fireEmployee);
    }

    public void setDetailsInfo(User user) {
        if (user == null) return;

        this.currentUser = user;

        this.detailName.setText(user.getEmployee().getName());
        this.detailSurname.setText(user.getEmployee().getSurname());
        this.detailBirthday.setText(user.getEmployee().getBirthday().toString());
        this.detailPost.setText(user.getEmployee().getPost());
        this.detailSalary.setText(user.getEmployee().getSalary() + " BYN");
        this.detailRole.setText(user.getRole().getName());
        this.detailLogin.setText(user.getLogin());
        this.detailMail.setText(user.getEmployee().getMail());
    }

    public void fireEmployee(ActionEvent actionEvent) {
        if (currentUser == null) return;

        try {
            ClientRequestBinder.instance.make(Request.deleteById, currentUser.getId());
            if (ClientRequestBinder.instance.receive()) EmployeesController.instance.reloadPage();
        } catch (Exception ignored) {}
    }

    public void showEditPane(ActionEvent actionEvent) {
        EmployeesController.instance.showEditingPage();
    }
}
