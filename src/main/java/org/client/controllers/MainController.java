package org.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.client.ClientStartup;
import org.client.services.TabMapper;

public class MainController {
    public TabPane mainTabPane;

    @FXML
    private void initialize() {
        loadTabs();
    }

    public void loadTabs() {
        String userRoleName = ClientStartup.clientAuthorization.getUser().getRole().getName();

        mainTabPane.getTabs().clear();
        switch (userRoleName) {
            case "User" -> {

            }
            case "Admin" -> {
                addTab(TabMapper.TabKey.employeesPage);
            }
            case "Manager" -> {

            }
        }
    }

    private void addTab(TabMapper.TabKey tabKey) {
        mainTabPane.getTabs().add(ClientStartup.tabMapper.getTab(tabKey));
    }
}
