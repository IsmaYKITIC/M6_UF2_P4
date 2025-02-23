package com.iticbcn.ismaelyounes.dao;

import com.iticbcn.ismaelyounes.model.Client;
import org.hibernate.SessionFactory;

public class ClientDAO extends GenDAOImpl<Client, Long> {
    public ClientDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Client.class);
    }
}