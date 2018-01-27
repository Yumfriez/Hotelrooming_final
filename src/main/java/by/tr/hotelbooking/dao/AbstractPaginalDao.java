package by.tr.hotelbooking.dao;

import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.pool.ConnectionPool;
import by.tr.hotelbooking.entities.Identified;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractPaginalDao<T extends Identified> extends AbstractDao<T> implements Paginal<T> {

    protected abstract String getRecordsWithOffsetQuery();
    protected abstract String getRecordsCountQuery();

    @Override
    public List<T> getRecordsWithOffset(int offset, int recordsCount) throws DAOException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<T> resultList;
        String getRecordsWithOffsetQuery = getRecordsWithOffsetQuery();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(getRecordsWithOffsetQuery);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, recordsCount);
            resultSet = preparedStatement.executeQuery();
            resultList = parseResultSet(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool",e);
        } catch (SQLException e) {
            throw new DAOException("Get records with offset from DB error ",e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return resultList;
    }

    @Override
    public int getRecordsCount() throws DAOException{
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result;
        String getRecordsCountQuery = getRecordsCountQuery();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(getRecordsCountQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Records counting in DB error ",e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return result;
    }
}
