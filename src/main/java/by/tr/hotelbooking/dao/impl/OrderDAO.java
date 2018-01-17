package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractJDBCDao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.entities.Order;
import by.tr.hotelbooking.entities.RoomType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO extends AbstractJDBCDao<Order> {

    private final static String GET_ALL_ORDERS = "SELECT account.login, order.order_id, order.places_count, order.date_in, order.days_count, "+
            "order.hotelroom_class FROM hotelrooms.account JOIN hotelrooms.order ON hotelrooms.order.u_id = hotelrooms.account.id";
    private static final String ADD_ORDER = "INSERT INTO hotelrooms.order (places_count, date_in, days_count, hotelroom_class, u_id) "+
            "VALUES (?, ?, ?, ?, (SELECT id FROM hotelrooms.account WHERE account.login=?))";
    private static final String REMOVE_ORDER = "DELETE FROM hotelrooms.order WHERE order_id=?";
    private static final String GET_LAST_ID = "SELECT order.order_id FROM hotelrooms.order ORDER BY order.order_id desc limit 1;";
    private static final String CHANGE_ORDER = "UPDATE hotelrooms.order SET places_count=?, date_in=?"+
            "days_count=?, hotelroom_class=? WHERE u_id=(SELECT id FROM hotelrooms.account WHERE login=?)";

    public OrderDAO() {

    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_ORDERS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return null;
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
                //order.setRoomType(RoomType.valueOf(rs.getString("hotelroom_class")));
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
        statement.setString(4, String.valueOf(order.getRoomType()));
        statement.setString(5, order.getAccountLogin());

    }

}
