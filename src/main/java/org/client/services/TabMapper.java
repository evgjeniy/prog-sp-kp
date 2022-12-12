package org.client.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.client.ClientStartup;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class TabMapper {
    public enum TabKey {
        employeesPage("employeesPage.fxml"),
        personalAccountPage("personalAccount.fxml");

        private final String value;
        public String getValue() { return this.value; }
        TabKey(String value) { this.value = value; }
    }


    private final Dictionary<TabKey, Tab> tabs;

    public TabMapper() {
        tabs = new Hashtable<>();
        loadTabs();
    }

    private void loadTabs() {
        try {
            tabs.put(TabKey.employeesPage, FXMLLoader.load(Objects.requireNonNull(
                    ClientStartup.class.getResource(TabKey.employeesPage.getValue()))));
            tabs.put(TabKey.personalAccountPage, FXMLLoader.load(Objects.requireNonNull(
                    ClientStartup.class.getResource(TabKey.personalAccountPage.getValue()))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Tab getTab(TabKey tabKey) {
        return tabs.get(tabKey);
    }
}

