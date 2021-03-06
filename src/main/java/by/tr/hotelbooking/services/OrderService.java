package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.Order;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.utils.LogicException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface OrderService {

    void createOrder(int placesCount, Date dateIn, int daysCount, int roomTypeId,
                     BigDecimal minPrice, BigDecimal maxPrice, String userLogin) throws ServiceException, LogicException;

    int getRecordsCount() throws ServiceException;
    int getPageNumber(String stringPageValue);
    int getPagesCount(int recordsCount) throws ServiceException;
    List<Order> getOrdersFromPage(int page) throws ServiceException;
    void removeOrder(int orderId) throws ServiceException;
}
