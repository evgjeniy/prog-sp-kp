package org.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.server.models.Candidate;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CandidatePageController {
    public Tab baseTab;
    public Label candidateFullName;
    public TextArea candidateSummary;
    public Label candidateBirthday;
    public Label candidateMail;
    public Label candidateTestUrl;

    @FXML private void initialize() {
        setCandidateInfo(VacanciesPageController.getSelectedCandidate());
    }

    private void setCandidateInfo(Candidate candidate) {
        baseTab.setText(candidate.getFullName());
        candidateFullName.setText(candidate.getFullName());
        candidateSummary.setText(candidate.getSummary());
        candidateBirthday.setText(candidate.getBirthday().toString());
        candidateMail.setText(candidate.getMail());
        candidateTestUrl.setText(candidate.getTestTaskUrl());
    }

    public void rejectCandidate(ActionEvent actionEvent) {

    }

    public void openTestUrl(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        try { Desktop.getDesktop().browse(new URI(candidateTestUrl.getText())); }
        catch (Exception ignored) {}
    }

    public void applyCandidate(ActionEvent actionEvent) {

    }
}