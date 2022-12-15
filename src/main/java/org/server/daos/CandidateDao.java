package org.server.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.server.db_connection.DbSessionFactory;
import org.server.models.Candidate;
import org.server.models.Vacancy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CandidateDao extends Dao<Candidate> {
    @Override
    public Candidate get(int id) {
        Candidate vacancy = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Candidate> criteriaQuery = criteriaBuilder.createQuery(Candidate.class);
            Root<Candidate> root = criteriaQuery.from(Candidate.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            vacancy = session.createQuery(criteriaQuery).getSingleResult();
            transaction.commit();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return vacancy;
    }

    @Override
    public List<Candidate> get() {
        List<Candidate> candidates = null;
        Session session = null;
        try {
            session = DbSessionFactory.getSessionFactory().openSession();
            candidates = session.createQuery("FROM Candidate", Candidate.class).list();
            session.close();
        } catch (Exception e) { e.printStackTrace(); }
        if (session != null) session.close();
        return candidates;
    }
}
