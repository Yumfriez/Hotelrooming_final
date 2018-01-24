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

public class AcceptContractCommand implements Command {
    private static Logger logger = Logger.getLogger(AcceptContractCommand.class);

    private final static AcceptContractCommand instance = new AcceptContractCommand();

    private AcceptContractCommand(){

    }

    public static AcceptContractCommand getInstance(){
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

            contractService.acceptContract(contractId);

            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?"+RequestParameter.COMMAND.getValue()
                    +"="+RequestParameter.SHOW_NEW_OFFERS.getValue());
        } catch (ValidatorException e) {
            logger.error(e);
        } catch (ServiceException e) {
            logger.error(e);
        }


    }
}
