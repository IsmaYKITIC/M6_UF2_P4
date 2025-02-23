package com.iticbcn.ismaelyounes.dao;

import com.iticbcn.ismaelyounes.model.Reserva;
import org.hibernate.SessionFactory;

public class ReservaDAO extends GenDAOImpl<Reserva, Long> {
    public ReservaDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Reserva.class);
    }
}