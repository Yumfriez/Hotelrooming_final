package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.ContractService;
import by.tr.hotelbooking.services.OrderService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddContractCommand implements Command {

    private static Logger logger = Logger.getLogger(AddContractCommand.class);

    private final static AddContractCommand instance = new AddContractCommand();

    private AddContractCommand(){

    }

    public static AddContractCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String hotelroomIdString = request.getParameter(RequestParameter.HOTELROOM_ID.getValue());
        String orderIdString = request.getParameter(RequestParameter.ORDER_ID.getValue());

        try {
            Validator.checkIsNotEmpty(hotelroomIdString, orderIdString);
            Validator.checkIsValidNumbers(hotelroomIdString, orderIdString);

            int hotelroomId = Integer.parseInt(hotelroomIdString);
            int orderId = Integer.parseInt(orderIdString);

            ContractService contractService = ServiceFactory.getInstance().getContractService();
            contractService.addContract(orderId, hotelroomId);
            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            orderService.removeOrder(orderId);

            ForwarRedirectChooser.doRedirect(response,servletPath, RequestCommandParameter.SHOW_ORDERS);

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
