package by.tr.hotelbooking.dao.factory;

import by.tr.hotelbooking.dao.impl.*;

public class DaoFactory {

    public DaoFactory() {

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