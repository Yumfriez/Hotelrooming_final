package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.Dao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.RoomTypeService;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.util.List;

public class RoomTypeServiceImpl implements RoomTypeService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public RoomTypeServiceImpl(){

    }

    @Override
    public List<RoomType> getAllRoomTypes() throws ServiceException{

        Dao<RoomType> roomTypeDao = daoFactory.getRoomTypeDAO();
        List<RoomType> roomTypeList;
        try {
            roomTypeList = roomTypeDao.getAll();
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return roomTypeList;
    }
}
