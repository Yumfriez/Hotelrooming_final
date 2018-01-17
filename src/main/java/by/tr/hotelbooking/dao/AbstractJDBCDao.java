package by.tr.hotelbooking.dao;

import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.pool.ConnectionPool;
import by.tr.hotelbooking.entities.Identified;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractJDBCDao<T extends Identified> implements Dao<T> {

    public AbstractJDBCDao() {

    }

    protected abstract String getSelectQuery();
    protected abstract String getSelectByPKQuery();
    protected abstract String getAddQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();
    protected abstract String getLastAdded();

    protected abstract List<T> parseResultSet(ResultSet rs) throws DAOException;
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DAOException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DAOException;

    @Override
    public T getByPK(Integer key) throws DAOException {
        List<T> list;
        String sql = getSelectByPKQuery();
        ConnectionPool connectionPool = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, key);
            ResultSet rs = preparedStatement.executeQuery();
            list = parseResultSet(rs);
            if (list == null || list.size() == 0) {
                return null;
            }
            if (list.size() > 1) {
                throw new DAOException("Received more than one record.");
            }
            return list.iterator().next();
        } catch (SQLException e) {
            throw new DAOException("Error occurred when tried get record by Primary Key " + e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Unable to give new connection from connection pool" , e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }

    }

    @Override
    public List<T> getAll() throws DAOException {
        List<T> list;
        String sql = getSelectQuery();
        ConnectionPool connectionPool = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            list = parseResultSet(rs);
            return list;

        } catch (SQLException e) {
            throw new DAOException("Error occurred when tried get all records from table " + e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Unable to give new connection from connection pool" , e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }

    }

    @Override
    public void update(T entity) throws DAOException {
        ConnectionPool connectionPool = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            String sql = getUpdateQuery();
            preparedStatement = connection.prepareStatement(sql);
            prepareStatementForUpdate(preparedStatement, entity);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new DAOException("Error occurred while updating record in DB " + e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool ", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(getDeleteQuery());
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Remove from DB error" + e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Unable to give new connection from connection pool" , e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }

    }

    @Override
    public void add(T entity) throws DAOException {
        ConnectionPool connectionPool = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            String sql = getAddQuery();
            preparedStatement = connection.prepareStatement(sql);
            prepareStatementForInsert(preparedStatement, entity);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new DAOException("Error occurred while adding a new record in DB " + e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool ", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }
    }
}
