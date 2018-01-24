package by.tr.hotelbooking.dao.factory;

import by.tr.hotelbooking.dao.impl.*;

public class DaoFactory {

    private final static DaoFactory instance = new DaoFactory();

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public CommentDAO getCommentDAO(){
        return new CommentDAO();
    }

    public ContractDAO getContractDAO(){
        return new ContractDAO();
    }

    public OrderDAO getOrderDAO(){
        return new OrderDAO();
    }

    public UserDAO getUserDAO(){
        return new UserDAO();
    }

    public HotelroomDAO getHotelroomDAO(){
        return new HotelroomDAO();
    }

    public RoomTypeDAO getRoomTypeDAO() { return  new RoomTypeDAO();}
}