package com.iticbcn.ismaelyounes.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.List;

public abstract class GenDAOImpl<T, ID> implements GenDAO<T, ID> {
    private final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    public GenDAOImpl(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    @Override
    public T get(ID id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.find(entityClass, id);
        } catch (HibernateException e) {
            throw new Exception("Error al obtener les entitats: " + e.getMessage(), e);
        }
    }

    @Override
    public List<T> getAll() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (HibernateException e) {
            throw new Exception("Error al obtener todas les entitats: " + e.getMessage(), e);
        }
    }

    @Override
    public void save(T t) throws Exception {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            throw new Exception("Error al guardar l'entitat: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(T t) throws Exception {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            throw new Exception("Error al actualizar l'entitat: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(T t) throws Exception {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            throw new Exception("Error al eliminar l'entitat: " + e.getMessage(), e);
        }
    }
}