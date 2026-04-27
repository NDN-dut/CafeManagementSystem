package com.cafe.dal;

import java.util.List;

public interface IBaseDAO<T, ID> {
    List<T> findAll();
    T findById(ID id);
    void insert(T entity);
    void update(T entity);
    void delete(ID id);
}