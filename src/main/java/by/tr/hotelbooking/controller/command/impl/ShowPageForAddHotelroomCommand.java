package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.dao.Dao;
import by.tr.hotelbooking.dao.impl.RoomTypeDAO;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.RoomTypeService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowPageForAddHotelroomCommand implements Command {

    private static Logger logger = Logger.getLogger(ShowPageForAddHotelroomCommand.class);



    private final static  ShowPageForAddHotelroomCommand instance = new ShowPageForAddHotelroomCommand();

    private ShowPageForAddHotelroomCommand(){

    }

    public static ShowPageForAddHotelroomCommand getInstance(){
        return instance;
    }



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {


        try {
            RoomTypeService roomTypeService = ServiceFactory.getInstance().getRoomTypeService();
            List<RoomType> roomTypeList = roomTypeService.getAllRoomTypes();

            request.setAttribute(RequestParameter.ROOM_TYPES_LIST.getValue(), roomTypeList);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.ADD_HOTELROOM_PAGE.getPath());
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.HOTELROOMS_PAGE.getPath());
        }


    }
}
