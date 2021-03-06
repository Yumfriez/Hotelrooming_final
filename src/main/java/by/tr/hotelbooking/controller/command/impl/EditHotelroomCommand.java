package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.entities.RoomType;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.RoomTypeService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import by.tr.hotelbooking.services.utils.LogicException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class EditHotelroomCommand implements Command {

    private static Logger logger = Logger.getLogger(EditHotelroomCommand.class);

    private final static EditHotelroomCommand instance = new EditHotelroomCommand();

    private EditHotelroomCommand(){

    }

    public static EditHotelroomCommand getInstance(){ return instance; }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(request.getSession().getAttribute(RequestParameter.LOGIN.getValue()+" try to edit hotelroom"));
        String servletPath = request.getServletPath();
        String hotelroomIdString = request.getParameter(RequestParameter.HOTELROOM_ID.getValue());
        String numberString = request.getParameter(RequestParameter.NUMBER.getValue());
        String placesCountString = request.getParameter(RequestParameter.PLACES.getValue());
        String floorString = request.getParameter(RequestParameter.FLOOR.getValue());
        String dailyPriceString = request.getParameter(RequestParameter.PRICE.getValue());
        String roomTypeIdString = request.getParameter(RequestParameter.ROOM_TYPE.getValue());
        String uploadDir = request.getServletContext().getRealPath("/");
        String foldersForFiles = request.getServletContext().getInitParameter("upload.location");

        try{
            Part part = request.getPart(RequestParameter.ROOM_IMAGE.getValue());

            Validator.checkIsNotEmpty(hotelroomIdString,numberString, placesCountString, floorString, dailyPriceString,
                    roomTypeIdString, uploadDir, part.getSubmittedFileName());
            Validator.checkIsValidNumbers(hotelroomIdString,numberString, floorString, placesCountString, roomTypeIdString);
            Validator.checkIsValidPrice(dailyPriceString);

            int hotelroomId = StringParser.parseFromStringToInt(hotelroomIdString);
            int number = StringParser.parseFromStringToInt(numberString);
            int floor = StringParser.parseFromStringToInt(floorString);
            int placesCount = StringParser.parseFromStringToInt(placesCountString);
            BigDecimal dailyPrice = StringParser.parseFromStringToBigDecimal(dailyPriceString);
            int roomTypeId = StringParser.parseFromStringToInt(roomTypeIdString);

            HotelroomService hotelroomService = ServiceFactory.getInstance().getHotelroomService();
            hotelroomService.editHotelroom(hotelroomId, number, placesCount, floor, dailyPrice,
                    roomTypeId, part, uploadDir+foldersForFiles);

            logger.debug(request.getSession().getAttribute(RequestParameter.LOGIN.getValue()+" edited hotelroom"));
            ForwarRedirectChooser.doRedirect(response, servletPath, RequestCommandParameter.SHOW_HOTELROOMS);

        } catch (ServletException | IOException |  ValidatorException | LogicException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            goToPreviousPage(request, response, hotelroomIdString);

        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            goToPreviousPage(request, response, hotelroomIdString);
        }
    }

    private void goToPreviousPage(HttpServletRequest request, HttpServletResponse response, String hotelroomIdString){
        try {
            Validator.checkIsNotEmpty(hotelroomIdString);
            Validator.checkIsValidNumbers(hotelroomIdString);

            int hotelroomId = StringParser.parseFromStringToInt(hotelroomIdString);

            HotelroomService hotelroomService = ServiceFactory.getInstance().getHotelroomService();
            Hotelroom hotelroom = hotelroomService.getHotelroomForEdit(hotelroomId);

            RoomTypeService roomTypeService = ServiceFactory.getInstance().getRoomTypeService();
            List<RoomType> roomTypeList = roomTypeService.getAllRoomTypes();

            request.setAttribute(RequestParameter.ROOM_TYPES_LIST.getValue(), roomTypeList);
            request.setAttribute(RequestParameter.HOTELROOM.getValue(), hotelroom);
            ForwarRedirectChooser.doForward(request, response, JspPageName.EDIT_HOTELROOM_PAGE.getPath());

        } catch (ValidatorException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        }

    }
}
