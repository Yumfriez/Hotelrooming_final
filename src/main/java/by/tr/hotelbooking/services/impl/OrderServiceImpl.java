package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.dao.impl.OrderDAO;
import by.tr.hotelbooking.entities.Order;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.OrderService;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    private DaoFactory daoFactory = new DaoFactory();

    @Override
    public void createOrder(int placesCount, Date dateIn, int daysCount, int roomTypeId,
                            BigDecimal minPrice, BigDecimal maxPrice, String userLogin) throws ServiceException {
        Order order = new Order();
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        order.setRoomType(roomType);
        order.setPreferedPlacesCount(placesCount);
        order.setAccountLogin(userLogin);
        order.setPreferedDateIn(dateIn);
        order.setDaysCount(daysCount);

        try {
            OrderDAO orderDAO = daoFactory.getOrderDAO();
            orderDAO.add(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int getPagesCount(int recordsCount) throws ServiceException {
        return (int) Math.ceil(recordsCount * 1.0 / 10);
    }

    @Override
    public List<Order> getOrdersFromPage(int page) throws ServiceException{
        List<Order> orders = null;
        try {
            OrderDAO orderDAO = daoFactory.getOrderDAO();
            orders = orderDAO.getOrdersForPage((page-1)*10,10);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public int getRecordsCount() throws ServiceException {
        int num = 0;
        try {
            num = daoFactory.getOrderDAO().getNumberOfOrders();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return num;
    }

    @Override
    public int getPageNumber(String stringPageValue) {
        int page;
        try {
            page = Integer.parseInt(stringPageValue);
        } catch (NumberFormatException e) {
            page = 1;
        }
        return page;
    }
}
