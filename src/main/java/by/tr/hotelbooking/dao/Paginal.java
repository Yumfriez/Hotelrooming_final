package by.tr.hotelbooking.dao;

import by.tr.hotelbooking.dao.exception.DAOException;

import java.util.List;

public interface Paginal<T> {
    List<T> getRecordsWithOffset(int offset, int recordsCount) throws DAOException;
    int getRecordsCount() throws DAOException;
}
