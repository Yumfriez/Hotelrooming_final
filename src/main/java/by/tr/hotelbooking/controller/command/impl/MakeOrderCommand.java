package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.OrderService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import by.tr.hotelbooking.services.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MakeOrderCommand implements Command {

    private static Logger logger = Logger.getLogger(MakeOrderCommand.class);

    private final static MakeOrderCommand instance = new MakeOrderCommand();

    private MakeOrderCommand(){}

    public static MakeOrderCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String placesCountString = request.getParameter(RequestParameter.PLACES.getValue());
        String minPriceString = request.getParameter(RequestParameter.MIN_PRICE.getValue());
        String maxPriceString = request.getParameter(RequestParameter.MAX_PRICE.getValue());
        String dateInString = request.getParameter(RequestParameter.DATE_IN.getValue());
        String daysCountString = request.getParameter(RequestParameter.DAYS_COUNT.getValue());
        String roomTypeIdString = request.getParameter(RequestParameter.ROOM_TYPE.getValue());
        HttpSession currentSession = request.getSession();
        String userLogin = (String) currentSession.getAttribute(RequestParameter.LOGIN.getValue());

        try {

            Validator.checkIsNotEmpty(placesCountString, minPriceString, maxPriceString, dateInString,
                    daysCountString, roomTypeIdString, userLogin);
            Validator.checkIsValidPrice(minPriceString, maxPriceString);
            Validator.checkIsValidNumbers(placesCountString, daysCountString, roomTypeIdString);
            Validator.checkIsValidLogin(userLogin);

            int placesCount = StringParser.parseFromStringToInt(placesCountString);
            BigDecimal minPrice = StringParser.parseFromStringToBigDecimal(minPriceString);
            BigDecimal maxPrice = StringParser.parseFromStringToBigDecimal(maxPriceString);
            int daysCount = StringParser.parseFromStringToInt(daysCountString);
            int roomTypeId = StringParser.parseFromStringToInt(roomTypeIdString);
            Date dateIn = StringParser.parseFromStringToDate(dateInString);

            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            orderService.createOrder(placesCount, dateIn, daysCount, roomTypeId,minPrice, maxPrice, userLogin);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?command=show_user_orders");
        } catch (ServiceException | ParseException e){
            logger.error(e);
        } catch (ValidatorException e) {
            logger.error(e+e.getMessage());
        }


    }
}
