package org.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.client.ClientStartup;
import org.client.services.TabsLocator;
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
        ClientStartup.tabsLocator.reloadTabs();
        switch (currentUser.getRole().getName()) {
            case "User", "Project Manager" -> {
                addTab(TabsLocator.TabKey.personalAccountPage);
                addTab(TabsLocator.TabKey.projectsPage);
            }
            case "HR Manager" -> {
                addTab(TabsLocator.TabKey.personalAccountPage);
                addTab(TabsLocator.TabKey.employeesPage);
                addTab(TabsLocator.TabKey.vacanciesPage);
            }
            case "Admin" -> {
                addTab(TabsLocator.TabKey.personalAccountPage);
                addTab(TabsLocator.TabKey.projectsPage);
                addTab(TabsLocator.TabKey.employeesPage);
                addTab(TabsLocator.TabKey.vacanciesPage);
            }
        }
    }

    private void addTab(TabsLocator.TabKey tabKey) {
        mainTabPane.getTabs().add(ClientStartup.tabsLocator.getTab(tabKey));
    }
}
