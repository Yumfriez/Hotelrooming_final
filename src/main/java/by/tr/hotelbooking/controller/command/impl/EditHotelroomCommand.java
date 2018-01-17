package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class EditHotelroomCommand implements Command {

    private static Logger logger = Logger.getLogger(EditHotelroomCommand.class);

    private final static EditHotelroomCommand instance = new EditHotelroomCommand();

    private EditHotelroomCommand(){

    }

    public static EditHotelroomCommand getInstance(){ return instance; }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HotelroomService hotelroomService = ServiceFactory.getInstance().getHotelroomService();

        logger.debug(request.getSession().getAttribute(RequestParameter.LOGIN.getValue()+" try to edit hotelroom"));
        try{
            String hotelroomIdString = request.getParameter(RequestParameter.HOTELROOM_ID.getValue());
            String numberString = request.getParameter(RequestParameter.NUMBER.getValue());
            String placesCountString = request.getParameter(RequestParameter.PLACES.getValue());
            String floorString = request.getParameter(RequestParameter.FLOOR.getValue());
            String dailyPriceString = request.getParameter(RequestParameter.PRICE.getValue());
            String roomTypeIdString = request.getParameter(RequestParameter.ROOM_TYPE.getValue());
            Part part = request.getPart(RequestParameter.ROOM_IMAGE.getValue());
            String uploadDir = request.getServletContext().getRealPath("/");
            hotelroomService.editHotelroom(hotelroomIdString, numberString, placesCountString, floorString, dailyPriceString,
                    roomTypeIdString, part, uploadDir);
            logger.debug(request.getSession().getAttribute(RequestParameter.LOGIN.getValue()+" edited hotelroom"));
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?command=show_hotelrooms");

        } catch (ServletException | IOException |ServiceException e) {
            logger.error(e);
        }
    }
}
