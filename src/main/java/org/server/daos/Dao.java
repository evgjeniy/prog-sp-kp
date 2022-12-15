package org.server.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.server.db_connection.DbSessionFactory;

import java.util.List;

public abstract class Dao<T> {
    public boolean insert(T entity) {
        boolean isAdded = false;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            session.close();
            isAdded = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isAdded;
    }

    public boolean update(T entity) {
        boolean isUpdated = false;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            session.close();
            isUpdated = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isUpdated;
    }

    public boolean delete(int id) {
        boolean isDeleted = false;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(get(id));
            transaction.commit();
            session.close();
            isDeleted = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isDeleted;
    }

    public abstract T get(int id);
    public abstract List<T> get();
}
