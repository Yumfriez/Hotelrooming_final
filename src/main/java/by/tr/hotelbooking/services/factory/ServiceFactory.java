package by.tr.hotelbooking.services.factory;


import by.tr.hotelbooking.services.impl.HotelroomServiceImpl;
import by.tr.hotelbooking.services.impl.RoomTypeServiceImpl;
import by.tr.hotelbooking.services.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private final HotelroomServiceImpl hotelroomService = HotelroomServiceImpl.getInstance();
    private final RoomTypeServiceImpl roomTypeService = RoomTypeServiceImpl.getInstance();


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

}

