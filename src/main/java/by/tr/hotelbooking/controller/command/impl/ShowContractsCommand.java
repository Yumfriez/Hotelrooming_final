package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.entities.Contract;
import by.tr.hotelbooking.services.ContractService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class ShowContractsCommand implements Command {
    private static Logger logger = Logger.getLogger(ShowContractsCommand.class);

    private final static ShowContractsCommand instance = new ShowContractsCommand();

    private ShowContractsCommand(){

    }

    public static ShowContractsCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ContractService contractService = ServiceFactory.getInstance().getContractService();
            int recordsCount = contractService.getRecordsCount();
            int pagesCount = contractService.getPagesCount(recordsCount);
            String pageStringValue = request.getParameter(RequestParameter.PAGINATION.getValue());
            int pageNumber = contractService.getPageNumber(pageStringValue);
            List<Contract> contracts = contractService.getContractsForPage(pageNumber);

            request.setAttribute(RequestParameter.CURRENT_DATE.getValue(), new Date());
            request.setAttribute(RequestParameter.CONTRACTS_LIST.getValue(), contracts);
            request.setAttribute(RequestParameter.PAGES_COUNT.getValue(), pagesCount);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER.getValue(), pageNumber);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestCommandParameter.SHOW_CONTRACTS.getValue());
            ForwarRedirectChooser.doForward(request,response, JspPageName.CONTRACTS_PAGE.getPath());

        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request,response, JspPageName.ADMIN_USER_PAGE.getPath());
        }

    }
}
