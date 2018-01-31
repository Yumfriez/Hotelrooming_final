package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.ContractService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineContractCommand implements Command {
    private static Logger logger = Logger.getLogger(DeclineContractCommand.class);

    private final static DeclineContractCommand instance = new DeclineContractCommand();

    private DeclineContractCommand(){

    }

    public static DeclineContractCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String contractIdString = request.getParameter(RequestParameter.CONTRACT_ID.getValue());

        try {
            Validator.checkIsNotEmpty(contractIdString);
            Validator.checkIsValidNumbers(contractIdString);

            int contractId = StringParser.parseFromStringToInt(contractIdString);

            ContractService contractService = ServiceFactory.getInstance().getContractService();
            contractService.declineContract(contractId);

            ForwarRedirectChooser.doRedirect(response, servletPath, RequestCommandParameter.SHOW_NEW_OFFERS);

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
