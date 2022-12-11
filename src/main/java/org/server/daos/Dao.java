package org.server.daos;

import java.util.List;

public interface Dao<T> {
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(int id);
    T get(int id);
    List<T> get();
}
