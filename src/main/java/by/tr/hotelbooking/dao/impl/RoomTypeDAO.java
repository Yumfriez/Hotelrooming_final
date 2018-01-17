package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractJDBCDao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.entities.RoomType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO extends AbstractJDBCDao<RoomType>{

    private final static String getAllRoomTypesQuery = "SELECT room_types.id, room_types.name "+
            "FROM room_types GROUP BY room_types.id";

    @Override
    protected String getSelectQuery() {
        return getAllRoomTypesQuery;
    }

    @Override
    protected String getSelectByPKQuery() {
        return null;
    }

    @Override
    protected String getAddQuery() {
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }

    @Override
    protected String getLastAdded() {
        return null;
    }

    @Override
    protected List<RoomType> parseResultSet(ResultSet rs) throws DAOException {
        List<RoomType> roomTypeList = new ArrayList<>();
        try {

            while (rs.next()) {
                RoomType roomType = new RoomType();
                roomType.setId(rs.getInt("id"));
                roomType.setName(rs.getString("name"));
                roomTypeList.add(roomType);
            }
        } catch (SQLException e){
            throw new DAOException(e);
        }
        return roomTypeList;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, RoomType object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, RoomType object) throws DAOException {

    }
}
