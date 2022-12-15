package org.client.services;

import org.classes.Request;
import org.server.models.*;

import java.io.IOException;
import java.util.List;

public class DataCollector {
    public static List<User> getUsers() throws IOException, ClassNotFoundException {
        ClientRequestBinder.instance.make(Request.getAllUsers);
        return ClientRequestBinder.instance.receive();
    }

    public static List<Project> getProjects() throws IOException, ClassNotFoundException {
        ClientRequestBinder.instance.make(Request.getAllProjects);
        return ClientRequestBinder.instance.receive();
    }

    public static List<Vacancy> getVacancies() throws IOException, ClassNotFoundException {
        ClientRequestBinder.instance.make(Request.getAllVacancies);
        return ClientRequestBinder.instance.receive();
    }

    public static List<Candidate> getCandidates() throws IOException, ClassNotFoundException {
        ClientRequestBinder.instance.make(Request.getAllCandidates);
        return ClientRequestBinder.instance.receive();
    }
}
