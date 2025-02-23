package com.iticbcn.ismaelyounes.dao;

import com.iticbcn.ismaelyounes.model.Empleat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EmpleatDAO extends GenDAOImpl<Empleat, Long> {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public EmpleatDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Empleat.class);
    }

    public Empleat obtenirEmpleatAmbRestaurant(long id) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT e FROM Empleat e LEFT JOIN FETCH e.restaurant WHERE e.id = :id";
            return session.createQuery(hql, Empleat.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("Error al obtener el Empleat: " + e.getMessage());
            return null;
        }
    }
}