package com.iticbcn.ismaelyounes.dao;

import java.util.List;

public interface GenDAO<T, ID> {
    T get(ID id) throws Exception;

    List<T> getAll() throws Exception;

    void save(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;
}
