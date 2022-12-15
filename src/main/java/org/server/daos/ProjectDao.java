package org.server.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.server.db_connection.DbSessionFactory;
import org.server.models.Employee;
import org.server.models.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProjectDao extends Dao<Project> {
    public ProjectDao() { get(); }

    @Override
    public boolean update(Project project) {
        boolean isUpdated = false;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            for (Employee employee : project.getEmployees()) session.update(employee);
            session.update(project);
            transaction.commit();
            session.close();
            isUpdated = true;
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return isUpdated;
    }

    @Override
    public List<Project> get() {
        List<Project> projects = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            projects = session.createQuery("FROM Project", Project.class).list();
            session.close();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return projects;
    }

    @Override
    public Project get(int id) {
        Project project = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
            Root<Project> root = criteriaQuery.from(Project.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            project = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return project;
    }
}
