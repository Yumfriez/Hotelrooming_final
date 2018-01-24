package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.OrderService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelOrderCommand implements Command{
    private static Logger logger = Logger.getLogger(CancelOrderCommand.class);

    private final static CancelOrderCommand instance = new CancelOrderCommand();

    private CancelOrderCommand(){

    }

    public static CancelOrderCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        OrderService orderService = ServiceFactory.getInstance().getOrderService();
        String orderIdString = request.getParameter(RequestParameter.ORDER_ID.getValue());

        try {
            Validator.checkIsNotEmpty(orderIdString);
            Validator.checkIsValidNumbers(orderIdString);
            int orderId = StringParser.parseFromStringToInt(orderIdString);

            orderService.removeOrder(orderId);
            ForwarRedirectChooser.doRedirect(response, servletPath, RequestCommandParameter.SHOW_ORDERS);
        } catch (ValidatorException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        }
    }
}
