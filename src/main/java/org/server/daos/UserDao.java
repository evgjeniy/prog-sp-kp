package org.server.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.server.db_connection.DbSessionFactory;
import org.server.models.Project;
import org.server.models.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao extends Dao<User> {
    public UserDao() { get(); }

    @Override
    public boolean insert(User user) {
        boolean isAdded = false;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user.getEmployee());
            session.save(user);
            transaction.commit();
            session.close();
            isAdded = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isAdded;
    }

    @Override
    public boolean update(User user) {
        boolean isUpdated = false;
        Session session = null;
        try {
            user.getEmployee().setId(get(user.getId()).getEmployee().getId());

            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            for (Project project : user.getEmployee().getProjects()) session.update(project);
            session.update(user.getEmployee());
            session.update(user);
            transaction.commit();
            session.close();
            isUpdated = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isUpdated;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleted = false;
        Session session = null;
        try {
            User user = get(id);

            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(user.getEmployee());
            session.delete(user);
            transaction.commit();
            session.close();
            isDeleted = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isDeleted;
    }

    @Override
    public List<User> get() {
        List<User> users = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            users = session.createQuery("FROM User", User.class).list();
            session.close();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return users;
    }

    @Override
    public User get(int id) {
        User user = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            user = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return user;
    }

    public User getByLogin(String login) {
        User user = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("login"), login));
            user = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return user;
    }
}
