package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AfterHotelroomOperationCommand implements Command {
    private static Logger logger = Logger.getLogger(AfterHotelroomOperationCommand.class);
    private AfterHotelroomOperationCommand(){

    }

    private final static  AfterHotelroomOperationCommand instance = new AfterHotelroomOperationCommand();

    public static AfterHotelroomOperationCommand getInstance(){
        return instance;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
        responseTypeChooser.doForward(request,response, JspPageName.HOTELROOMS_PAGE.getPath());

        logger.debug("Successful redirect after operation with hotelroom");
    }
}
