package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractPaginalDao;
import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.pool.ConnectionPool;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.entities.HotelroomDTO;
import by.tr.hotelbooking.entities.RoomType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelroomDAO extends AbstractPaginalDao<Hotelroom> {

    private final static String GET_HOTELROOMS_FOR_PAGE = "SELECT hotelroom.id, hotelroom.number, hotelroom.places_count, hotelroom.daily_price, "+
            "hotelroom.floor, hotelroom.imageName, room_types.name FROM hotelrooms.hotelroom INNER JOIN hotelrooms.room_types ON " +
            "hotelroom.room_type_id = room_types.id GROUP BY hotelroom.number LIMIT ?,?";
    private final static String GET_HOTELROOM_BY_ID = "SELECT hotelroom.id, hotelroom.number, hotelroom.places_count, hotelroom.daily_price, "+
            "hotelroom.floor, hotelroom.imageName, room_types.name FROM hotelrooms.hotelroom INNER JOIN hotelrooms.room_types ON " +
            "hotelroom.room_type_id = room_types.id WHERE hotelroom.id=?";
    private final static String GET_ALL_HOTELROOMS = "SELECT hotelroom.id, hotelroom.number, hotelroom.places_count, hotelroom.daily_price, "+
            "hotelroom.floor, hotelroom.imageName, room_types.name FROM hotelrooms.hotelroom INNER JOIN hotelrooms.room_types ON " +
            "hotelroom.room_type_id = room_types.id";
    private static final String REMOVE_HOTELROOM_QUERY = "DELETE FROM hotelrooms.hotelroom WHERE hotelroom.id=?";
    private static final String ADD_HOTELROOM = "INSERT INTO hotelrooms.hotelroom (number, places_count, daily_price, floor, imageName, room_type_id) "+
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CHANGE_HOTELROOM = "UPDATE hotelrooms.hotelroom SET number=?, places_count=?, "+
            "daily_price=?, floor=?, imageName=?, room_type_id=? WHERE id=?";

    private static final String GET_HOTELROOM_BY_CRITERIA = "SELECT hotelroom.id, hotelroom.number, hotelroom.places_count, hotelroom.daily_price, "+
            "hotelroom.floor, hotelroom.imageName, room_types.name FROM hotelrooms.hotelroom INNER JOIN hotelrooms.room_types ON " +
            "hotelroom.room_type_id = room_types.id LEFT JOIN contract ON hotelroom.id = contract.contract_id "+
            "WHERE hotelroom.places_count>=? AND (hotelroom.daily_price BETWEEN ? AND ?) AND hotelroom.room_type_id = ? "+
            "AND (contract.contract_id IS NULL OR (date_in>? OR date_out<?))"+
            "GROUP BY hotelroom.number LIMIT ?,?";

    private static final String GET_COUNT = "SELECT count(*) FROM hotelrooms.hotelroom";

    private static final String GET_COUNT_WITH_PARAMS = "SELECT count(*) FROM hotelrooms.hotelroom INNER JOIN hotelrooms.room_types ON " +
            "hotelroom.room_type_id = room_types.id LEFT JOIN contract ON hotelroom.id = contract.contract_id "+
            "WHERE hotelroom.places_count>=? AND (hotelroom.daily_price BETWEEN ? AND ?) AND hotelroom.room_type_id = ? "+
            "AND (contract.contract_id IS NULL OR (date_in>? OR date_out<?))";
    public HotelroomDAO() {

    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_HOTELROOMS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return GET_HOTELROOM_BY_ID;
    }

    @Override
    protected String getAddQuery() {
        return ADD_HOTELROOM;
    }

    @Override
    protected String getUpdateQuery() {
        return CHANGE_HOTELROOM;
    }

    @Override
    protected String getDeleteQuery() {
        return REMOVE_HOTELROOM_QUERY;
    }

    @Override
    protected String getRecordsWithOffsetQuery() {
        return GET_HOTELROOMS_FOR_PAGE;
    }

    @Override
    protected String getRecordsCountQuery() {
        return GET_COUNT;
    }

    @Override
    protected List<Hotelroom> parseResultSet(ResultSet rs) throws DAOException {
        List<Hotelroom> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Hotelroom hotelroom=new Hotelroom();
                setHotelroomParameters(hotelroom, rs);
                result.add(hotelroom);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Hotelroom hotelroom) throws DAOException {
        try {
            fillNonDeleteStatement(statement,hotelroom);
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Hotelroom hotelroom) throws DAOException {
        try {
            fillNonDeleteStatement(statement,hotelroom);
            statement.setInt(7, hotelroom.getId());
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }


    public List<Hotelroom> getHotelroomsByParameters(HotelroomDTO hotelroomDTO, int count, int offset) throws DAOException{
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        List<Hotelroom> hotelrooms = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_HOTELROOM_BY_CRITERIA);
            fillFindCriterias(preparedStatement, hotelroomDTO);
            preparedStatement.setInt(7, count);
            preparedStatement.setInt(8, offset);
            resultSet = preparedStatement.executeQuery();
            hotelrooms = new ArrayList<>();
            while (resultSet.next()) {
                Hotelroom hotelroom = new Hotelroom();
                setHotelroomParameters(hotelroom, resultSet);
                hotelrooms.add(hotelroom);
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool",e);
        } catch (SQLException e) {
            throw new DAOException("Get all hotelrooms from DB error " + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return hotelrooms;
    }

    public int getNumberOfHotelroomsByParams(HotelroomDTO hotelroomDTO) throws DAOException{
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(GET_COUNT_WITH_PARAMS);
            fillFindCriterias(preparedStatement, hotelroomDTO);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);

        } catch (ConnectionPoolException e) {
            throw new DAOException("Unable to give new connection from connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Hotelrooms counting in DB error "+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        return result;
    }

    private void setHotelroomParameters(Hotelroom hotelroom, ResultSet resultSet) throws SQLException {
        hotelroom.setId(resultSet.getInt("id"));
        hotelroom.setNumber(resultSet.getInt("number"));
        hotelroom.setPlacesCount(resultSet.getInt("places_count"));
        hotelroom.setDailyPrice(resultSet.getBigDecimal("daily_price"));
        hotelroom.setFloor(resultSet.getInt("floor"));
        hotelroom.setImageName(resultSet.getString("imageName"));
        RoomType roomType = new RoomType();
        roomType.setName(resultSet.getString("name"));
        hotelroom.setRoomType(roomType);
    }

    private void fillFindCriterias(PreparedStatement preparedStatement, HotelroomDTO hotelroomDTO) throws SQLException{

        int placesCount = hotelroomDTO.getPlacesCount();
        preparedStatement.setInt(1, placesCount);
        BigDecimal minPrice = hotelroomDTO.getMinPrice();
        preparedStatement.setBigDecimal(2, minPrice);
        BigDecimal maxPrice = hotelroomDTO.getMaxPrice();
        preparedStatement.setBigDecimal(3, maxPrice);
        RoomType roomType = hotelroomDTO.getRoomType();
        Integer roomTypeId = roomType.getId();
        preparedStatement.setInt(4, roomTypeId);
        preparedStatement.setDate(5, hotelroomDTO.getDateOut());
        preparedStatement.setDate(6, hotelroomDTO.getDateIn());

    }

    private void fillNonDeleteStatement(PreparedStatement statement, Hotelroom hotelroom) throws SQLException {
        statement.setInt(1, hotelroom.getNumber());
        statement.setInt(2, hotelroom.getPlacesCount());
        statement.setBigDecimal(3, hotelroom.getDailyPrice());
        statement.setInt(4, hotelroom.getFloor());
        statement.setString(5, hotelroom.getImageName());
        int roomTypeId = hotelroom.getRoomType().getId();
        statement.setInt(6, roomTypeId);

    }

}
