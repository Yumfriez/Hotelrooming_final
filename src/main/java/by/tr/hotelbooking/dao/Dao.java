package by.tr.hotelbooking.dao;

import by.tr.hotelbooking.dao.exception.DAOException;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {
    void add(T object) throws DAOException;
    T getByPK(Integer key) throws DAOException;
    void update(T object) throws DAOException;
    void delete(Integer id) throws DAOException;
    List<T> getAll() throws DAOException;
}
