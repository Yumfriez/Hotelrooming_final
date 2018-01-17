package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.services.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.List;

public interface HotelroomService {
    void addHotelroom(String number, String placesCount, String floor,  String dailyPrice,
                      String roomTypeId, Part part, String uploadDir) throws ServiceException;
    Hotelroom getHotelroomForEdit(Integer hotelroomId) throws ServiceException;
    void editHotelroom(String hoteroomIdString, String number, String placesCount, String floor,  String dailyPrice,
                       String roomTypeId, Part part, String uploadDir) throws ServiceException;
    List<Hotelroom> getAllHotelrooms() throws ServiceException;
    int getPagesCount(int recordsCount) throws ServiceException;
    List<Hotelroom> getHotelroomsFromPage(int page) throws ServiceException;
    int getRecordsCount() throws ServiceException;
    int getPageNumber(String stringPageValue);
    void deleteHotelroom(String idString) throws ServiceException;
}
