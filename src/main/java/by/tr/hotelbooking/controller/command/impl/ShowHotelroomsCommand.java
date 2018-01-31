package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.entities.Hotelroom;
import by.tr.hotelbooking.services.HotelroomService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowHotelroomsCommand implements Command {
    private static Logger logger = Logger.getLogger(ShowHotelroomsCommand.class);

    private static final ShowHotelroomsCommand instance = new ShowHotelroomsCommand();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private ShowHotelroomsCommand(){

    }

    public static Command getInstance(){
        return instance;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadDirectory = request.getServletContext().getInitParameter("upload.location");

        try {
            HotelroomService hotelroomService = serviceFactory.getHotelroomService();
            int recordsCount = hotelroomService.getRecordsCount();
            int pagesCount = hotelroomService.getPagesCount(recordsCount);
            String pageStringValue = request.getParameter(RequestParameter.PAGINATION.getValue());
            int pageNumber = hotelroomService.getPageNumber(pageStringValue);
            List<Hotelroom> hotelrooms = hotelroomService.getHotelroomsFromPage(pageNumber);

            request.setAttribute(RequestParameter.HOTELROOMS_LIST.getValue(), hotelrooms);
            request.setAttribute(RequestParameter.PAGES_COUNT.getValue(), pagesCount);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER.getValue(), pageNumber);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestCommandParameter.SHOW_HOTELROOMS.getValue());
            request.setAttribute(RequestParameter.FILES_DIRECTORY.getValue(), uploadDirectory);
            ForwarRedirectChooser.doForward(request,response, JspPageName.HOTELROOMS_PAGE.getPath());

        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request,response, JspPageName.ADMIN_USER_PAGE.getPath());
        }
    }
}
