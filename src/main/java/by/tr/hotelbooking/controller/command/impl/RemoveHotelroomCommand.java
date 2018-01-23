package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveHotelroomCommand implements Command {

    private static Logger logger = Logger.getLogger(RemoveHotelroomCommand.class);

    private static final RemoveHotelroomCommand instance = new RemoveHotelroomCommand();


    private RemoveHotelroomCommand(){

    }

    public static RemoveHotelroomCommand getInstance(){ return instance;}

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HotelroomService hotelroomService = ServiceFactory.getInstance().getHotelroomService();
        String idString = request.getParameter(RequestParameter.HOTELROOM_ID.getValue());


        try {
            Validator.checkIsNotEmpty(idString);
            Validator.checkIsValidNumbers(idString);

            int hotelroomId = StringParser.parseFromStringToInt(idString);

            hotelroomService.deleteHotelroom(hotelroomId);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?command=show_hotelrooms");
        } catch (ServiceException e) {
            logger.error(e);
        } catch (ValidatorException e) {
            logger.error(e+e.getMessage());
        }

    }
}
