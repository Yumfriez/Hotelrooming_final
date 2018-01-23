package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FindHotelroomsCommand implements Command {

    private static Logger logger = Logger.getLogger(FindHotelroomsCommand.class);

    private final static FindHotelroomsCommand instance = new FindHotelroomsCommand();

    private FindHotelroomsCommand(){

    }

    public static FindHotelroomsCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String placesCountString = request.getParameter(RequestParameter.PLACES.getValue());
        String minPriceString = request.getParameter(RequestParameter.MIN_PRICE.getValue());
        String maxPriceString = request.getParameter(RequestParameter.MAX_PRICE.getValue());
        String dateInString = request.getParameter(RequestParameter.DATE_IN.getValue());
        String daysCountString = request.getParameter(RequestParameter.DAYS_COUNT.getValue());
        String clientLogin = request.getParameter(RequestParameter.CLIENT_LOGIN.getValue());
        String roomTypeIdString = request.getParameter(RequestParameter.ROOM_TYPE.getValue());
        String pageStringValue = request.getParameter(RequestParameter.PAGINATION.getValue());

        try {
            Validator.checkIsNotEmpty(placesCountString, minPriceString, maxPriceString, dateInString,
                    daysCountString, clientLogin, roomTypeIdString);
            Validator.checkIsValidNumbers(placesCountString, daysCountString, roomTypeIdString);
            Validator.checkIsValidPrice(minPriceString, maxPriceString);
            Validator.checkIsValidLogin(clientLogin);

            int placesCount = StringParser.parseFromStringToInt(placesCountString);
            BigDecimal minPrice = StringParser.parseFromStringToBigDecimal(minPriceString);
            BigDecimal maxPrice = StringParser.parseFromStringToBigDecimal(maxPriceString);
            int daysCount = StringParser.parseFromStringToInt(daysCountString);
            int roomTypeId = StringParser.parseFromStringToInt(roomTypeIdString);
            Date dateIn = StringParser.parseFromStringToDate(dateInString);

            HotelroomService hotelroomService = ServiceFactory.getInstance().getHotelroomService();

            int pageNumber = hotelroomService.getPageNumber(pageStringValue);
            int recordsCount = hotelroomService.getRecordsByCriteriaCount(placesCount, minPrice, maxPrice, roomTypeId, dateIn, daysCount);
            int pagesCount = hotelroomService.getPagesCount(recordsCount);
            List<Hotelroom> hotelrooms = hotelroomService.findHotelroomsForPage(placesCount, minPrice, maxPrice, roomTypeId, dateIn, daysCount,pageNumber);
            request.setAttribute(RequestParameter.HOTELROOMS_LIST.getValue(), hotelrooms);
            request.setAttribute(RequestParameter.PAGES_COUNT.getValue(), pagesCount);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER.getValue(), pageNumber);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestParameter.SHOW_HOTELROOMS.getValue());

            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.HOTELROOMS_PAGE.getPath());
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.ORDERS_PAGE.getPath());
        } catch (ValidatorException | ParseException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.ORDERS_PAGE.getPath());
        }

    }
}
