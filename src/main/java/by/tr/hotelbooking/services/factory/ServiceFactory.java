package by.tr.hotelbooking.services.factory;


import by.tr.hotelbooking.services.CommentService;
import by.tr.hotelbooking.services.OrderService;
import by.tr.hotelbooking.services.impl.*;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private final HotelroomServiceImpl hotelroomService = HotelroomServiceImpl.getInstance();
    private final RoomTypeServiceImpl roomTypeService = RoomTypeServiceImpl.getInstance();
    private final OrderService orderService = new OrderServiceImpl();
    private final CommentService commentService = new CommentServiceImpl();


    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public RoomTypeServiceImpl getRoomTypeService() {
        return roomTypeService;
    }

    public UserServiceImpl getUserService() {
        return userService;
    }
    public HotelroomServiceImpl getHotelroomService(){ return hotelroomService;}
    public OrderService getOrderService() {
        return orderService;
    }
    public CommentService getCommentService(){
        return commentService;
    }

}

