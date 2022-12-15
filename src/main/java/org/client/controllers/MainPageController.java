package org.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.client.ClientStartup;
import org.client.services.TabMapper;
import org.server.models.User;

public class MainPageController {
    public static MainPageController instance;
    public TabPane mainTabPane;

    @FXML private void initialize() {
        instance = this;
        loadTabs();
    }

    public void loadTabs() {
        User currentUser = ClientStartup.clientAuthorization.getUser();

        mainTabPane.getTabs().clear();
        ClientStartup.tabMapper.reloadTabs();
        switch (currentUser.getRole().getName()) {
            case "User" -> {
                addTab(TabMapper.TabKey.personalAccountPage);
            }
            case "Project Manager" -> {
                addTab(TabMapper.TabKey.personalAccountPage);
                addTab(TabMapper.TabKey.projectsPage);
            }
            case "HR Manager" -> {
                addTab(TabMapper.TabKey.personalAccountPage);
                addTab(TabMapper.TabKey.vacanciesPage);
            }
            case "Admin" -> {
                addTab(TabMapper.TabKey.personalAccountPage);
                addTab(TabMapper.TabKey.employeesPage);
                addTab(TabMapper.TabKey.projectsPage);
                addTab(TabMapper.TabKey.vacanciesPage);
            }
        }
    }

    private void addTab(TabMapper.TabKey tabKey) {
        mainTabPane.getTabs().add(ClientStartup.tabMapper.getTab(tabKey));
    }
}
