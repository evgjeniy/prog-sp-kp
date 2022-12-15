package org.server.db_connection;

import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.server.models.*;

public class DbSessionFactory {
    private static SessionFactory sessionFactory;

    private static void createSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();

            // --- add annotation model classes to configuration ---
            configuration.addAnnotatedClass(Project.class);
            configuration.addAnnotatedClass(Status.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Role.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Vacancy.class);
            configuration.addAnnotatedClass(Candidate.class);

            sessionFactory = configuration.buildSessionFactory(
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

        } catch (HibernateException e) {
            System.out.println("Creating session factory error");
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) createSessionFactory();
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory == null) return;
        sessionFactory.close();
        sessionFactory = null;
    }
}