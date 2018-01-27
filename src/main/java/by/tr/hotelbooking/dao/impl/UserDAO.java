package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractDao;
import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.pool.ConnectionPool;
import by.tr.hotelbooking.entities.Role;
import by.tr.hotelbooking.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDao<User> {
    private static final String SIGN_IN = "SELECT u_id, login, pass, name, surname, email, role, block_status, locale "+
            " FROM hotelrooms.account WHERE login=? AND pass=?";
    private static final String SIGN_UP = "INSERT INTO hotelrooms.account (login, pass, name, surname, " +
            "email, locale) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String GET_LAST_ID = "SELECT account.id FROM hotelrooms.account ORDER BY account.id desc limit 1;";
    private final static String GET_ALL_USERS = "SELECT u_id, login, pass, name, surname, email, role, block_status, locale "+
            " FROM hotelrooms.account";
    private static final String CHANGE_USER = "UPDATE hotelrooms.account SET pass=?, name=?, surname=?,"+
            " email=?, role=?, block_status=?, WHERE login=?";
    private static final String GET_USER_BY_LOGIN = "SELECT u_id, login, pass, name, surname, email, role, block_status, locale " +
            "FROM hotelrooms.account WHERE login=?";
    private static final String UPDATE_LOCALE = "UPDATE hotelrooms.account SET locale=? WHERE login=?";
    private static final String UPDATE_BLOCK_STATUS = "UPDATE account SET block_status = ? WHERE login = ?";

    public UserDAO() {
    }
    
    public void setBlockStatus(String login, boolean blockStatus) throws DAOException {
        ConnectionPool connectionPool=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(UPDATE_BLOCK_STATUS);
            preparedStatement.setBoolean(1, blockStatus);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Block user operation error in DB ", e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }
    }

    public User signIn(User user) throws DAOException{

        ConnectionPool connectionPool=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(SIGN_IN);
            String login = user.getLogin();
            preparedStatement.setString(1, login);
            String password = user.getPassword();
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = createUser(resultSet);
            return user;
        }catch (SQLException e){
            throw new DAOException("Sign in operation error in DB ", e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }

    }

    public User findUser(String login) throws DAOException{
        ConnectionPool connectionPool=null;
        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        User user=null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN);
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user=createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return user;
    }
    
    public void updateLocale(String login, String locale) throws DAOException{
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(UPDATE_LOCALE);
            ps.setString(2, login);
            ps.setString(1, locale);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (update locale)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_USERS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return null;
    }

    @Override
    protected String getAddQuery() {
        return SIGN_UP;
    }

    @Override
    protected String getUpdateQuery() {
        return CHANGE_USER;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }


    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DAOException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User user= createUser(rs);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws DAOException {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getLocale());
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws DAOException {
        try {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getEmail());
            statement.setString(5, String.valueOf(user.getRole()));
            statement.setBoolean(6, user.getIsBlocked());
            statement.setString(7, user.getLogin());
            statement.setString(8, user.getLocale());
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        Integer id = resultSet.getInt("u_id");
        user.setId(id);
        String login = resultSet.getString("login");
        user.setLogin(login);
        String password = resultSet.getString("pass");
        user.setPassword(password);
        String name = resultSet.getString("name");
        user.setName(name);
        String surname = resultSet.getString("surname");
        user.setSurname(surname);
        String email = resultSet.getString("email");
        user.setEmail(email);
        Role role = Role.valueOf(resultSet.getString("role"));
        user.setRole(role);
        Boolean blockStatus = resultSet.getBoolean("block_status");
        user.setBlocked(blockStatus);
        String locale = resultSet.getString("locale");
        user.setLocale(locale);
        return user;
    }

}
