package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.utils.LogicException;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface HotelroomService {
    void addHotelroom(int number, int placesCount, int floor,  BigDecimal dailyPrice,
                      int roomTypeId, Part part, String uploadDir) throws ServiceException, LogicException;
    Hotelroom getHotelroomForEdit(Integer hotelroomId) throws ServiceException;
    void editHotelroom(int hotelroomId, int number, int placesCount, int floor,  BigDecimal dailyPrice,
                       int roomTypeId, Part part, String uploadDir) throws ServiceException, LogicException;
    List<Hotelroom> getAllHotelrooms() throws ServiceException;
    int getPagesCount(int recordsCount) throws ServiceException;
    List<Hotelroom> getHotelroomsFromPage(int page) throws ServiceException;
    List<Hotelroom> findHotelroomsForPage(int placesCount, BigDecimal minPrice, BigDecimal maxPrice, int roomTypeId,
                                          Date dateIn, int daysCount, int page) throws ServiceException, LogicException;
    int getRecordsByCriteriaCount(int placesCount, BigDecimal minPrice, BigDecimal maxPrice, int roomTypeId,
                                  Date dateIn, int daysCount) throws ServiceException, LogicException;
    int getRecordsCount() throws ServiceException;
    int getPageNumber(String stringPageValue);
    void deleteHotelroom(int id) throws ServiceException;
}
