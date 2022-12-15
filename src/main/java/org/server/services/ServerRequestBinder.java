package org.server.services;

import org.classes.Request;
import org.server.ServerStartup;
import org.server.daos.CandidateDao;
import org.server.daos.ProjectDao;
import org.server.daos.UserDao;
import org.server.daos.VacancyDao;
import org.server.models.Project;
import org.server.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServerRequestBinder {
    public static ServerRequestBinder instance;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public static final UserDao userDao = new UserDao();
    public static final ProjectDao projectDao = new ProjectDao();
    public static final VacancyDao vacancyDao = new VacancyDao();
    public static final CandidateDao candidateDao = new CandidateDao();

    public ServerRequestBinder() {
        instance = this;
    }

    public void map(Request request) throws IOException, ClassNotFoundException {
        switch (request) {
            case getAllUsers -> outputStream.writeObject(new ArrayList<>(userDao.get()));
            case getAllProjects -> outputStream.writeObject(new ArrayList<>(projectDao.get()));
            case getAllVacancies -> outputStream.writeObject(new ArrayList<>(vacancyDao.get()));
            case getAllCandidates -> outputStream.writeObject(new ArrayList<>(candidateDao.get()));

            case login -> {
                String[] form = ((String)inputStream.readObject()).split(";");
                outputStream.writeObject(ServerAuthorization.login(form[0], form[1]));
            }
            case getUserById -> {
                int id = (int) inputStream.readObject();
                outputStream.writeObject(userDao.get(id));
            }
            case getUserByLogin -> {
                String login = (String) inputStream.readObject();
                outputStream.writeObject(userDao.getByLogin(login));
            }
            case insertUser -> {
                User user = (User) inputStream.readObject();
                outputStream.writeObject(userDao.insert(user));
            }
            case deleteUserById -> {
                int id = (int) inputStream.readObject();
                outputStream.writeObject(userDao.delete(id));
            }
            case updateUser -> {
                User user = (User) inputStream.readObject();
                outputStream.writeObject(userDao.update(user));
            }
            case updateProject -> {
                Project project = (Project) inputStream.readObject();
                outputStream.writeObject(projectDao.update(project));
            }
        }
    }

    public <T> T receive() throws IOException, ClassNotFoundException {
        return (T) inputStream.readObject();
    }

    public void configure(ObjectInputStream in, ObjectOutputStream out) {
        this.inputStream = in;
        this.outputStream = out;
    }
}
