package org.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.client.ClientStartup;
import org.client.services.DataCollector;
import org.server.models.Candidate;
import org.server.models.Vacancy;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class VacanciesPageController {
    public TextField searchField;
    public ListView<String> vacanciesList;
    public Label vacancyPost;
    public TextArea vacancyDescription;
    public TextArea vacancyTestDescription;
    public Label vacancySalary;
    public ChoiceBox<String> candidatesChoiceBox;

    private ObservableList<Vacancy> vacanciesListValues;

    private static Candidate selectedCandidate;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        loadVacancies();
        search(searchField.getText());
        setVacancyInfo(vacanciesListValues.get(0));
    }

    public void addVacancy(ActionEvent actionEvent) {}

    public void deleteVacancy(ActionEvent actionEvent) {}

    private void loadVacancies() throws IOException, ClassNotFoundException {
        List<Vacancy> v = DataCollector.getVacancies();
        vacanciesListValues = FXCollections.observableList(v);
    }

    private void search(String searchString) {
        if (searchString.isBlank()) {
            vacanciesList.setItems(FXCollections.observableList(vacanciesListValues.
                    stream().map(Vacancy::getPost).toList()));
        } else {
            vacanciesList.setItems(FXCollections.observableList(vacanciesListValues.
                    stream().map(Vacancy::getPost).filter(post -> post.equals(searchString)).toList()));
        }
    }

    public void selectVacancy(MouseEvent mouseEvent) {
        String selectedVacancy = ((ListView<String>)mouseEvent.getSource()).getSelectionModel().getSelectedItem();
        if (selectedVacancy == null) return;

        List<Vacancy> result = vacanciesListValues.stream().filter(x -> x.getPost().contains(selectedVacancy)).toList();
        if (result.size() > 0) setVacancyInfo(result.get(0));
    }

    private void setVacancyInfo(Vacancy vacancy) {
        if (vacancy == null) return;

        vacancyPost.setText(vacancy.getPost());
        vacancyDescription.setText(vacancy.getDescription());
        vacancyTestDescription.setText(vacancy.getTestDescription());
        vacancySalary.setText(String.valueOf(vacancy.getSalary()));
        candidatesChoiceBox.setItems(FXCollections.observableList(
                FXCollections.observableList(vacancy.getCandidates().stream().toList())
                        .stream().map(Candidate::getFullName).toList()
        ));

        candidatesChoiceBox.setOnAction(e -> {
            System.out.println("ON ACTION!");

            String candidateName = ((ChoiceBox<String>)e.getSource()).getSelectionModel().getSelectedItem();
            if (candidateName == null) return;

            System.out.println("ON CANDIDATE FOUNDED: " + candidateName);

            List<Candidate> result = vacancy.getCandidates().stream().
                    filter(x -> x.getFullName().equals(candidateName)).toList();
            if (result.size() > 0) {
                selectedCandidate = result.get(0);
                try {
                    Tab candidateTab = FXMLLoader.load(Objects.requireNonNull(
                            ClientStartup.class.getResource("candidatePage.fxml")));
                    MainPageController.instance.mainTabPane.getTabs().add(candidateTab);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static Candidate getSelectedCandidate() { return selectedCandidate; }
}