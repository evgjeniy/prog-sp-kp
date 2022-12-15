package org.client.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.client.ClientStartup;

import java.io.IOException;
import java.util.*;

public class TabsLocator {
    public enum TabKey {
        employeesPage("employeesPage.fxml"),
        personalAccountPage("personalAccount.fxml"),
        projectsPage("projectsPage.fxml"),
        vacanciesPage("vacanciesPage.fxml");

        private final String value;
        public String getValue() { return this.value; }
        TabKey(String value) { this.value = value; }
    }


    private Dictionary<TabKey, Tab> tabs;

    public void reloadTabs() {
        tabs = new Hashtable<>();
        try {
            tabs.put(TabKey.employeesPage, FXMLLoader.load(Objects.requireNonNull(
                    ClientStartup.class.getResource(TabKey.employeesPage.getValue()))));
            tabs.put(TabKey.personalAccountPage, FXMLLoader.load(Objects.requireNonNull(
                    ClientStartup.class.getResource(TabKey.personalAccountPage.getValue()))));
            tabs.put(TabKey.projectsPage, FXMLLoader.load(Objects.requireNonNull(
                    ClientStartup.class.getResource(TabKey.projectsPage.getValue()))));
            tabs.put(TabKey.vacanciesPage, FXMLLoader.load(Objects.requireNonNull(
                    ClientStartup.class.getResource(TabKey.vacanciesPage.getValue()))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Tab getTab(TabKey tabKey) {
        if (tabs == null) reloadTabs();
        return tabs.get(tabKey);
    }
}

