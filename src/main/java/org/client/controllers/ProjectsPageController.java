package org.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.classes.Request;
import org.client.ClientStartup;
import org.client.services.ClientRequestBinder;
import org.client.services.DataCollector;
import org.server.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public VBox leftVerticalBox;

    public static Project currentProject;

    ObservableList<Project> currentUserProjects;
    ObservableList<Project> allProjects;
    ObservableList<User> allUsers;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        loadProjects();
        search(searchField.getText());
        setDetailsInfo(allProjects.get(0));

        if (ClientStartup.clientAuthorization.getUser().getRole().getName().equals("User")) {
            int size = leftVerticalBox.getChildren().size();
            leftVerticalBox.getChildren().remove(leftVerticalBox.getChildren().get(size - 1));
            leftVerticalBox.getChildren().remove(leftVerticalBox.getChildren().get(size - 2));
            leftVerticalBox.getChildren().remove(leftVerticalBox.getChildren().get(size - 3));
        }
    }

    private void loadProjects() throws IOException, ClassNotFoundException {
        currentUserProjects = FXCollections.observableList(
                ClientStartup.clientAuthorization.getUser().getEmployee().getProjects().stream().toList());

        allProjects = FXCollections.observableList(DataCollector.getProjects());
        allUsers = FXCollections.observableList(DataCollector.getUsers());
    }

    public void search(ActionEvent actionEvent) { search(searchField.getText()); }

    private void search(String searchString) {
        Stream<String> userProjectNames = currentUserProjects.stream().map(Project::getName);
        Stream<String> allProjectNames = allProjects.stream().map(Project::getName);

        if (!searchString.isBlank()) {
            userProjectNames = userProjectNames.filter(name -> name.contains(searchString));
            allProjectNames = allProjectNames.filter(name -> name.contains(searchString));
        }

        currentUserProjectViewList.setItems(FXCollections.observableList(userProjectNames.toList()));
        allProjectViewList.setItems(FXCollections.observableList(allProjectNames.toList()));
    }

    public void addEmployeeToProject(ActionEvent actionEvent) {
        String employeeFullName = ((ComboBox<String>)actionEvent.getSource()).getSelectionModel().getSelectedItem();

        List<User> result = allUsers.stream().filter(x -> x.getFullName().equals(employeeFullName)).toList();
        if (result.size() > 0) {
            Employee employeeToAdd = result.get(0).getEmployee();
            employeeToAdd.getProjects().add(currentProject);
            currentProject.getEmployees().add(employeeToAdd);

            try {
                ClientRequestBinder.instance.make(Request.updateProject, currentProject);
                if (ClientRequestBinder.instance.receive()) reloadPage();
            } catch (Exception e) { e.printStackTrace(); }
        }
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
        for (String todoItem : project.getTodo().split("\n")) addTodoItem(todoItem);

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

    private void deleteTodoItem(ActionEvent actionEvent) {
        CheckBox checkBox = (CheckBox)actionEvent.getSource();
        if (checkBox == null) return;

        todoCheckboxesParent.getChildren().remove(checkBox);

        List<String> todoList = new ArrayList<>(Arrays.stream(currentProject.getTodo().split("\n")).toList());
        for (String todoItem : todoList) {
            if (todoItem.equals(checkBox.getText())) {
                todoList.remove(todoItem);
                break;
            }
        }
        currentProject.setTodo(String.join("\n", todoList));
        try {
            ClientRequestBinder.instance.make(Request.updateProject, currentProject);
            if (ClientRequestBinder.instance.receive()) reloadPage();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public void addTodoItem(ActionEvent actionEvent) {
        addTodoItem(newTaskField.getText());
        newTaskField.clear();
    }

    private void addTodoItem(String newTodoItem) {
        if (newTodoItem.isBlank()) return;

        CheckBox newCheckbox = new CheckBox(newTodoItem);
        newCheckbox.setOnAction(this::deleteTodoItem);
        todoCheckboxesParent.getChildren().add(newCheckbox);

        if (currentProject.getTodo().contains(newTodoItem)) return;

        try {
            currentProject.setTodo(currentProject.getTodo() + "\n" + newTodoItem);
            ClientRequestBinder.instance.make(Request.updateProject, currentProject);
            if (ClientRequestBinder.instance.receive()) reloadPage();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void reloadPage() throws IOException, ClassNotFoundException {
        loadProjects();

        searchField.clear();
        search(searchField.getText());
        setDetailsInfo(allProjects.get(0));
    }
}