package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
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

        ContractService contractService = ServiceFactory.getInstance().getContractService();
        String contractIdString = request.getParameter(RequestParameter.CONTRACT_ID.getValue());

        try {
            Validator.checkIsNotEmpty(contractIdString);
            Validator.checkIsValidNumbers(contractIdString);
            int contractId = StringParser.parseFromStringToInt(contractIdString);

            contractService.declineContract(contractId);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?"+RequestParameter.COMMAND.getValue()+"="+RequestParameter.SHOW_NEW_OFFERS.getValue());
        } catch (ValidatorException e) {
            logger.error(e);
        } catch (ServiceException e) {
            logger.error(e);
        }
    }
}
