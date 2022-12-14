package org.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.classes.Request;
import org.client.ClientStartup;
import org.client.services.ClientRequestBinder;
import org.server.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectsPageController {
    public TextField searchField;
    public ListView<String> currentUserProjectViewList;
    public ListView<String> allProjectViewList;
    public Label projectName;
    public Label deadline;
    public TextArea description;
    public VBox todoCheckboxesParent;
    public TextField newTaskField;
    public VBox projectEmployees;
    public ComboBox<String> chooseEmployees;

    public static Project currentProject;

    ObservableList<Project> currentUserProjects;
    ObservableList<Project> allProjects;
    ObservableList<User> allUsers;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        loadProjects();
        defaultSearch();
        setDetailsInfo(allProjects.get(0));
    }

    private void loadProjects() throws IOException, ClassNotFoundException {
        currentUserProjects = FXCollections.observableList(
                ClientStartup.clientAuthorization.getUser().getEmployee().getProjects().stream().toList());

        ClientRequestBinder.instance.make(Request.getAllProjects);
        ArrayList<Project> allProjectsArray = ClientRequestBinder.instance.receive();

        allProjects = FXCollections.observableArrayList(allProjectsArray);

        ClientRequestBinder.instance.make(Request.getAllClients);
        allUsers = FXCollections.observableList(ClientRequestBinder.instance.receive());

        defaultSearch();
    }

    private void defaultSearch() {
        ObservableList<String> projectNames = FXCollections.observableArrayList(
                currentUserProjects.stream().map(Project::getName).collect(Collectors.toList()));

        currentUserProjectViewList.setItems(FXCollections.observableList(projectNames));

        // all projects set view;

        allProjectViewList.setItems(
                FXCollections.observableList(allProjects.stream().map(Project::getName).toList()));
    }

    public void addEmployeeToProject(ActionEvent actionEvent) {
        ComboBox<String> comboBox = (ComboBox<String>)actionEvent.getSource();
        String string = comboBox.getSelectionModel().getSelectedItem();

        System.out.println(string);
    }

    public void selectProject(MouseEvent mouseEvent) {
        String projectName = ((ListView<String>)mouseEvent.getSource()).getSelectionModel().getSelectedItem();
        if (projectName == null) return;

        List<Project> result = allProjects.stream().filter(x -> x.getName().contains(projectName)).toList();
        if (result.size() > 0) setDetailsInfo(result.get(0));
    }

    public void setDetailsInfo(Project project) {
        if (project == null) return;

        currentProject = project;

        projectName.setText(project.getName());
        deadline.setText(project.getDeadline().toString());
        description.setText(project.getDescription());

        todoCheckboxesParent.getChildren().clear();
        for (String todoItem : project.getTodo().split("\n")) addTask(todoItem);

        projectEmployees.getChildren().clear();
        for (Employee employee : project.getEmployees())
            projectEmployees.getChildren().add(new Label(employee.getFullName()));

        ObservableList<String> usersToAdd = FXCollections.observableList(allUsers.stream().filter(x -> {
            for (Employee employee : project.getEmployees())
                if (employee.getUser().getId() == x.getId()) return false;
            return true;
        }).map(User::getFullName).collect(Collectors.toList()));

        chooseEmployees.setItems(usersToAdd);
    }

    private void deleteCheckbox(ActionEvent actionEvent) {
        CheckBox checkBox = (CheckBox)actionEvent.getSource();
        if (checkBox != null) todoCheckboxesParent.getChildren().remove(checkBox);
    }

    public void addNewTask(ActionEvent actionEvent) {
        addTask(newTaskField.getText());
        newTaskField.clear();
    }

    private void addTask(String taskDescription) {
        if (taskDescription.isBlank()) return;

        CheckBox newCheckbox = new CheckBox(taskDescription);
        newCheckbox.setOnAction(this::deleteCheckbox);
        todoCheckboxesParent.getChildren().add(newCheckbox);
    }
}
