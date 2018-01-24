package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractJDBCDao;
import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.pool.ConnectionPool;
import by.tr.hotelbooking.entities.Order;
import by.tr.hotelbooking.entities.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractJDBCDao<Order> {

    private final static String GET_ALL_ORDERS = "SELECT account.login, order.order_id, order.places_count, order.date_in, order.days_count, "+
            " room_types.name FROM hotelrooms.order JOIN hotelrooms.order ON hotelrooms.order.u_id = hotelrooms.account.id "+
            " JOIN hotelrooms.order ON hotelrooms.order.room_type_id = room_types.id";
    private final static String GET_ORDERS_FOR_PAGE = "SELECT account.login, orders.order_id, orders.places_count, orders.date_in,orders.days_count, "+
            " orders.min_daily_price, orders.max_daily_price, room_types.id, room_types.name FROM hotelrooms.orders JOIN hotelrooms.account ON hotelrooms.orders.u_id = hotelrooms.account.u_id "+
            " JOIN hotelrooms.room_types ON hotelrooms.orders.room_type_id = room_types.id GROUP BY orders.order_id LIMIT ?,?";
    private final static String GET_ORDER_BY_ID = "SELECT account.login, orders.order_id, orders.places_count, orders.date_in,orders.days_count, "+
            " orders.min_daily_price, orders.max_daily_price, room_types.id, room_types.name FROM hotelrooms.orders JOIN hotelrooms.account ON hotelrooms.orders.u_id = hotelrooms.account.u_id "+
            " JOIN hotelrooms.room_types ON hotelrooms.orders.room_type_id = room_types.id WHERE orders.order_id = ?";
    private static final String ADD_ORDER = "INSERT INTO hotelrooms.orders (places_count, date_in, days_count, room_type_id, min_daily_price, " +
            " max_daily_price, u_id) VALUES (?, ?, ?, ?, ?, ?, (SELECT u_id FROM hotelrooms.account WHERE account.login=?))";
    private static final String REMOVE_ORDER = "DELETE FROM hotelrooms.orders WHERE order_id=?";
    private static final String GET_LAST_ID = "SELECT order.order_id FROM hotelrooms.order ORDER BY order.order_id desc limit 1;";
    private static final String CHANGE_ORDER = "UPDATE hotelrooms.order SET places_count=?, date_in=?"+
            "days_count=?, hotelroom_class=? WHERE u_id=(SELECT u_id FROM hotelrooms.account WHERE login=?)";
    private static final String GET_COUNT = "SELECT count(*) from hotelrooms.orders";

    public OrderDAO() {

    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_ORDERS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return GET_ORDER_BY_ID;
    }

    @Override
    protected String getAddQuery() {
        return ADD_ORDER;
    }

    @Override
    protected String getUpdateQuery() {
        return CHANGE_ORDER;
    }

    @Override
    protected String getDeleteQuery() {
        return REMOVE_ORDER;
    }

    @Override
    protected String getLastAdded() {
        return GET_LAST_ID;
    }

    public List<Order> getOrdersForPage(int pageNumber, int offset) throws DAOException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<Order> orders;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_ORDERS_FOR_PAGE);
            preparedStatement.setInt(1, pageNumber);
            preparedStatement.setInt(2, offset);
            resultSet = preparedStatement.executeQuery();
            orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                setOrderParameters(order, resultSet);
                orders.add(order);
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool",e);
        } catch (SQLException e) {
            throw new DAOException("Get all orders from DB error " + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return orders;
    }

    public int getNumberOfOrders() throws DAOException{
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_COUNT);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Orders counting in DB error " + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return result;
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws DAOException {
        List<Order> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Order order=new Order();
                order.setId(rs.getInt("id"));
                order.setPreferedPlacesCount(rs.getInt("places_count"));
                order.setPreferedDateIn(rs.getDate("date_in"));
                order.setDaysCount(rs.getInt("days_count"));
                order.setMinPrice(rs.getBigDecimal("min_daily_price"));
                order.setMaxPrice(rs.getBigDecimal("max_daily_price"));
                RoomType roomType = new RoomType();
                roomType.setName(rs.getString("name"));
                order.setRoomType(roomType);
                order.setAccountLogin(rs.getString("login"));
                result.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order order) throws DAOException {
        try {
            fillNonDeleteStatement(statement,order);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Order order) throws DAOException {
        try {
            fillNonDeleteStatement(statement, order);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void fillNonDeleteStatement(PreparedStatement statement, Order order) throws SQLException{
        statement.setInt(1, order.getPreferedPlacesCount());
        statement.setDate(2, order.getPreferedDateIn());
        statement.setInt(3, order.getDaysCount());
        RoomType roomType = order.getRoomType();
        statement.setInt(4, roomType.getId());
        statement.setBigDecimal(5, order.getMinPrice());
        statement.setBigDecimal(6, order.getMaxPrice());
        statement.setString(7, order.getAccountLogin());
    }

    private void setOrderParameters(Order order, ResultSet resultSet) throws SQLException{
        order.setId(resultSet.getInt("order_id"));
        order.setMinPrice(resultSet.getBigDecimal("min_daily_price"));
        order.setMaxPrice(resultSet.getBigDecimal("max_daily_price"));
        order.setPreferedDateIn(resultSet.getDate("date_in"));
        order.setDaysCount(resultSet.getInt("days_count"));
        order.setPreferedPlacesCount(resultSet.getInt("places_count"));
        order.setAccountLogin(resultSet.getString("login"));
        RoomType roomType = new RoomType();
        roomType.setId(resultSet.getInt("id"));
        roomType.setName(resultSet.getString("name"));
        order.setRoomType(roomType);

    }

}
