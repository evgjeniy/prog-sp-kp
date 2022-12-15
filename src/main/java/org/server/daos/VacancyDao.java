package org.server.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.server.db_connection.DbSessionFactory;
import org.server.models.Vacancy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class VacancyDao extends Dao<Vacancy> {
    @Override
    public Vacancy get(int id) {
        Vacancy vacancy = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Vacancy> criteriaQuery = criteriaBuilder.createQuery(Vacancy.class);
            Root<Vacancy> root = criteriaQuery.from(Vacancy.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            vacancy = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return vacancy;
    }

    @Override
    public List<Vacancy> get() {
        List<Vacancy> vacancies = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            vacancies = session.createQuery("FROM Vacancy", Vacancy.class).list();
            session.close();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return vacancies;
    }
}
