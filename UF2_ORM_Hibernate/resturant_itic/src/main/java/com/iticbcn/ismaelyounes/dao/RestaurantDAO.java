package com.iticbcn.ismaelyounes.dao;

import com.iticbcn.ismaelyounes.model.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class RestaurantDAO extends GenDAOImpl<Restaurant, Long> {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public RestaurantDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Restaurant.class);
    }

    // Método específico para obtener un restaurante por su nombre
    public Restaurant obtenirRestaurantPerNom(String nom) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Restaurant WHERE nom = :nom";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("nom", nom);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error al obtener el Restaurant: " + e.getMessage());
            return null;
        }
    }
}