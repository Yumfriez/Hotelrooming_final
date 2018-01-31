package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.entities.User;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUsers implements Command {
    private static Logger logger = Logger.getLogger(ShowUsers.class);

    private static final ShowUsers instance = new ShowUsers();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private ShowUsers() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<User> userList = serviceFactory.getUserService().getAllUsers();
            request.setAttribute( RequestParameter.USER_LIST.getValue(), userList);

            ForwarRedirectChooser.doForward(request,response, JspPageName.USERS_PAGE.getPath());

        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        }

    }
}
