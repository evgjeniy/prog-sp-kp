package org.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.client.ClientStartup;
import org.client.services.TabMapper;
import org.server.models.User;

public class MainController {
    public TabPane mainTabPane;

    @FXML
    private void initialize() {
        loadTabs();
    }

    public void loadTabs() {
        User currentUser = ClientStartup.clientAuthorization.getUser();
        PersonalAccountController.instance.setUserPersonalData(currentUser);

        mainTabPane.getTabs().clear();
        switch (currentUser.getRole().getName()) {
            case "User" -> {
                addTab(TabMapper.TabKey.personalAccountPage);
            }
            case "Admin", "Manager" -> {
                addTab(TabMapper.TabKey.personalAccountPage);
                addTab(TabMapper.TabKey.employeesPage);
            }
        }
    }

    private void addTab(TabMapper.TabKey tabKey) {
        mainTabPane.getTabs().add(ClientStartup.tabMapper.getTab(tabKey));
    }
}
