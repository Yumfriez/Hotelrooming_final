package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.entities.Order;
import by.tr.hotelbooking.services.OrderService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowOrdersCommand implements Command {

    private Logger logger = Logger.getLogger(ShowOrdersCommand.class);

    private final static ShowOrdersCommand instance = new ShowOrdersCommand();

    private ShowOrdersCommand(){}

    public static ShowOrdersCommand getInstance(){
        return instance;
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        OrderService orderService = ServiceFactory.getInstance().getOrderService();

        try {
            int recordsCount = orderService.getRecordsCount();
            int pagesCount = orderService.getPagesCount(recordsCount);
            String pageStringValue = request.getParameter(RequestParameter.PAGINATION.getValue());
            int pageNumber = orderService.getPageNumber(pageStringValue);
            List<Order> orders = orderService.getOrdersFromPage(pageNumber);
            request.setAttribute(RequestParameter.ORDERS_LIST.getValue(), orders);
            request.setAttribute(RequestParameter.PAGES_COUNT.getValue(), pagesCount);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER.getValue(), pageNumber);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request,response, JspPageName.ORDERS_PAGE.getPath());
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request,response, JspPageName.ADMIN_USER_PAGE.getPath());
        }

    }
}
