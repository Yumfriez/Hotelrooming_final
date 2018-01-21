package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
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

        OrderService orderService = ServiceFactory.getInstance().getOrderService();

        int placesCount = Integer.parseInt(placesCountString);
        BigDecimal minPrice = new BigDecimal(minPriceString);
        BigDecimal maxPrice = new BigDecimal(maxPriceString);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //REFACTOR
        Date dateIn = null;
        try {
            java.util.Date dateValue = dateFormat.parse(dateInString);
            dateIn = new Date(dateValue.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int daysCount = Integer.parseInt(daysCountString);
        int roomTypeId = Integer.parseInt(roomTypeIdString);

        //REFACTOR
        try {
            orderService.createOrder(placesCount, dateIn, daysCount, roomTypeId,minPrice, maxPrice, userLogin);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?command=show_user_orders");
        } catch (ServiceException e){
            logger.error(e);
        }



    }
}
