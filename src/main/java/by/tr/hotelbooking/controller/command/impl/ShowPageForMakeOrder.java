package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.RoomTypeService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowPageForMakeOrder implements Command {

    private static Logger logger = Logger.getLogger(ShowPageForMakeOrder.class);

    private final static ShowPageForMakeOrder instance = new ShowPageForMakeOrder();

    private ShowPageForMakeOrder(){

    }

    public static ShowPageForMakeOrder getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            RoomTypeService roomTypeService = ServiceFactory.getInstance().getRoomTypeService();
            List<RoomType> roomTypeList = roomTypeService.getAllRoomTypes();

            request.setAttribute(RequestParameter.ROOM_TYPES_LIST.getValue(), roomTypeList);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.MAKE_ORDER_PAGE.getPath());
        } catch (ServiceException e){
            logger.error(e);
        }

    }
}
