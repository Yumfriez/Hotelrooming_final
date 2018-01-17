package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAllRoomTypes() throws ServiceException;
}
