package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.RoomTypeService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import by.tr.hotelbooking.services.impl.HotelroomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowPageForEditHotelroomCommand implements Command {

    private static Logger logger = Logger.getLogger(ShowPageForEditHotelroomCommand.class);

    private final static  ShowPageForEditHotelroomCommand instance = new ShowPageForEditHotelroomCommand();

    private ShowPageForEditHotelroomCommand(){

    }

    public static ShowPageForEditHotelroomCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            RoomTypeService roomTypeService = ServiceFactory.getInstance().getRoomTypeService();
            List<RoomType> roomTypeList = roomTypeService.getAllRoomTypes();

            HotelroomService hotelroomService = ServiceFactory.getInstance().getHotelroomService();
            String hotelroomIdString = request.getParameter(RequestParameter.HOTELROOM_ID.getValue());
            Integer hotelroomId = Integer.parseInt(hotelroomIdString);
            Hotelroom hotelroom = hotelroomService.getHotelroomForEdit(hotelroomId);

            request.setAttribute(RequestParameter.ROOM_TYPES_LIST.getValue(), roomTypeList);
            request.setAttribute(RequestParameter.HOTELROOM.getValue(), hotelroom);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.EDIT_HOTELROOM_PAGE.getPath());
        } catch (ServiceException e){
            logger.error(e);
        }

    }
}
