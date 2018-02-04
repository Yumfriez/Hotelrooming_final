package by.tr.hotelbooking.services.factory;


import by.tr.hotelbooking.services.*;
import by.tr.hotelbooking.services.impl.*;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public RoomTypeService getRoomTypeService() {
        return new RoomTypeServiceImpl();
    }
    public UserService getUserService() {
        return new UserServiceImpl();
    }
    public HotelroomService getHotelroomService(){ return new HotelroomServiceImpl();}
    public OrderService getOrderService() {
        return new OrderServiceImpl();
    }
    public CommentService getCommentService(){
        return new CommentServiceImpl();
    }
    public ContractService getContractService() {
        return new ContractServiceImpl();
    }
}

