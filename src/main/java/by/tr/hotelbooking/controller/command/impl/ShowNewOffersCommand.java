package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.*;
import by.tr.hotelbooking.entities.ContractDTO;
import by.tr.hotelbooking.services.ContractService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowNewOffersCommand implements Command {
    private static Logger logger = Logger.getLogger(ShowNewOffersCommand.class);

    private final static ShowNewOffersCommand instance = new ShowNewOffersCommand();

    private ShowNewOffersCommand(){

    }

    public static ShowNewOffersCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String userLogin = (String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue());
        String uploadDirectory = request.getServletContext().getInitParameter("upload.location");

        try {
            ContractService contractService = ServiceFactory.getInstance().getContractService();
            int recordsCount = contractService.getRecordsCount();
            int pagesCount = contractService.getPagesCount(recordsCount);
            String pageStringValue = request.getParameter(RequestParameter.PAGINATION.getValue());
            int pageNumber = contractService.getPageNumber(pageStringValue);
            List<ContractDTO> contracts = contractService.getNewUserOffers(pageNumber, userLogin);

            request.setAttribute(RequestParameter.OFFERS_LIST.getValue(), contracts);
            request.setAttribute(RequestParameter.PAGES_COUNT.getValue(), pagesCount);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER.getValue(), pageNumber);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestCommandParameter.SHOW_NEW_OFFERS.getValue());
            request.setAttribute(RequestParameter.FILES_DIRECTORY.getValue(), uploadDirectory);
            ForwarRedirectChooser.doForward(request,response, JspPageName.OFFERS_PAGE.getPath());

        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request,response, JspPageName.ADMIN_USER_PAGE.getPath());
        }
    }
}
