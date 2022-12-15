package org.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.client.ClientStartup;
import org.client.services.DataCollector;
import org.server.models.User;

import java.io.IOException;
import java.util.*;

public class EmployeesPageController {
    public static EmployeesPageController instance;
    public TextField searchField;
    public ListView<String> employeeListViews;
    public Pane rightPane;

    public VBox cachedDetailsBox;
    public VBox cachedAddingBox;

    private ObservableList<User> employeesListValues;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        instance = this;

        loadChildPages();
        reloadPage();
    }

    private void loadChildPages() throws IOException {
        cachedDetailsBox = FXMLLoader.load(Objects.requireNonNull(
                ClientStartup.class.getResource("employeeDetails.fxml")));

        cachedAddingBox = FXMLLoader.load(Objects.requireNonNull(
                ClientStartup.class.getResource("employeeAdding.fxml")));

    }

    private void loadEmployees() throws IOException, ClassNotFoundException {
        employeesListValues = FXCollections.observableList(DataCollector.getUsers());
    }

    public void search(String searchString) {
        if (searchString.isBlank()) {
            employeeListViews.setItems(FXCollections.observableList(employeesListValues.
                    stream().map(User::getFullName).toList()));
        } else {
            employeeListViews.setItems(FXCollections.observableList(employeesListValues.
                    stream().map(User::getFullName).filter(name -> name.contains(searchString)).toList()));
        }
    }

    public void search(ActionEvent inputMethodEvent) { search(searchField.getText()); }

    public void showDetailsInfo(MouseEvent mouseEvent) {
        String searchable = ((ListView<String>)mouseEvent.getSource()).getSelectionModel().getSelectedItem();
        if (searchable == null) return;

        List<User> result = employeesListValues.stream().filter(x -> x.getFullName().contains(searchable)).toList();
        if (result.size() > 0) setDetails(result.get(0));
    }

    private void setDetails(User resultUser) {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(cachedDetailsBox);

        EmployeeDetailsController.instance.setDetailsInfo(resultUser);
    }

    public void showAddingPane(ActionEvent actionEvent) {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(cachedAddingBox);
        EmployeeAddingController.instance.setAddingMode();
    }

    public void showEditingPage() {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(cachedAddingBox);
        EmployeeAddingController.instance.setEditingMode();
    }

    public void  reloadPage() throws IOException, ClassNotFoundException {
        loadEmployees();

        searchField.clear();
        search(searchField.getText());
        setDetails(employeesListValues.get(0));
    }
}
