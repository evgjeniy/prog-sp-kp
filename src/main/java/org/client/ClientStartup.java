package org.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.client.services.ClientAuthorization;
import org.client.services.ClientRequestBinder;
import org.client.services.TabsLocator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientStartup extends Application {
    public static final ClientAuthorization clientAuthorization = new ClientAuthorization();
    public static final ClientRequestBinder requestMapper = new ClientRequestBinder();
    public static TabsLocator tabsLocator;
    private static Stage stage;
    private static String startPageName = "login.fxml";

    public static void main(String[] connectionAddress) {
        try {
            Socket clientSocket = new Socket(connectionAddress[0], Integer.parseInt(connectionAddress[1]));

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            requestMapper.configure(in, out);
            tabsLocator = new TabsLocator();

            launch();

            clientSocket.close();
        } catch (IOException e) {
            startPageName = "errorPage.fxml";
            launch();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        loadPage(startPageName);

        stage.setTitle("IT Management System");
        stage.setResizable(false);
        stage.show();
    }

    public static void loadPage(String pageName) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClientStartup.class.getResource(pageName));

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
    }
}