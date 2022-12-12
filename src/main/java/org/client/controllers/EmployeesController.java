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
import org.classes.Request;
import org.client.ClientStartup;
import org.server.models.User;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeesController {
    public static EmployeesController instance;
    public TextField searchField;
    public ListView<String> employeeListViews;
    public Pane rightPane;

    public VBox cachedDetailsBox;
    public VBox cachedAddingBox;

    ObservableList<User> employeesListValues = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        instance = this;

        cachedDetailsBox = FXMLLoader.load(Objects.requireNonNull(
                ClientStartup.class.getResource("employeeDetails.fxml")));

        cachedAddingBox = FXMLLoader.load(Objects.requireNonNull(
                ClientStartup.class.getResource("employeeAdding.fxml")));

        loadEmployees();
        defaultSearch();
    }

    private void loadEmployees() throws IOException, ClassNotFoundException {
        ClientStartup.requestMapper.make(Request.getAllClients);
        ArrayList<User> users = ClientStartup.requestMapper.receive();

        employeesListValues = FXCollections.observableList(users);
    }

    public void defaultSearch() {
        ObservableList<String> fullNames = FXCollections.observableArrayList(
                employeesListValues.stream().map(User::getFullName).collect(Collectors.toList()));

        employeeListViews.setItems(fullNames);
        if (employeesListValues.size() > 0) setDetails(employeesListValues.get(0));
    }

    public void searchByName(ActionEvent inputMethodEvent) {
        String searchText = searchField.getText();
        if (searchText.isBlank() || searchText.isEmpty()) { defaultSearch(); return; }

        ObservableList<String> filteredFullNames = FXCollections.observableArrayList(employeesListValues.
                stream().map(User::getFullName).filter(fullName -> fullName.contains(searchText)).
                collect(Collectors.toList()));

        employeeListViews.setItems(filteredFullNames);
    }

    public void showDetailsInfo(MouseEvent mouseEvent) {
        String searchable = employeeListViews.getSelectionModel().getSelectedItem();
        if (searchable == null) return;

        List<User> result = employeesListValues.stream().filter(x -> x.getFullName().contains(searchable)).toList();
        if (result.size() > 0) setDetails(result.get(0));
    }

    private void setDetails(User resultUser) {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(cachedDetailsBox);

        EmployeeDetailsController.instance.setDetailsInfo(resultUser);
    }

    public void showAddingPane(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(cachedAddingBox);
        EmployeeAddingController.instance.setAddingMode();
    }

    public void showEditingPage() {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(cachedAddingBox);
        EmployeeAddingController.instance.setEditingMode();
    }

    public void reloadPage() throws IOException, ClassNotFoundException {
        loadEmployees();
        defaultSearch();

        Optional<User> optional = employeesListValues.stream().max(Comparator.comparingInt(User::getId));
        optional.ifPresent(this::setDetails);
    }
}
