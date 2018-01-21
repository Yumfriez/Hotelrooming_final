package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.entities.User;
import by.tr.hotelbooking.services.UserService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Redirect implements Command {
    private static Logger logger = Logger.getLogger(Redirect.class);

    private static final Redirect instance = new Redirect();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private Redirect() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        JspPageName jspPageName = null;
        logger.debug("Attempt to restore session");
        Object role = request.getSession().getAttribute(RequestParameter.ROLE.getValue());
        if (role != null) {
            jspPageName = JspPageName.ADMIN_USER_PAGE;
        } else {
            jspPageName = readCookies(request);
        }

        ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
        responseTypeChooser.doForward(request,response,jspPageName.getPath());
    }

    private JspPageName readCookies(HttpServletRequest request) {
        logger.debug("Reading data from coockies...");
        JspPageName jspPageName = null;
        Cookie cookies[] = request.getCookies();
        boolean hasCookieLogin = false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RequestParameter.LOGIN.getValue())) {
                hasCookieLogin = true;
                try {
                    UserService userService = serviceFactory.getUserService();
                    User user = userService.getUserByLogin(cookie.getValue());
                    HttpSession session = request.getSession();
                    session.setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
                    session.setAttribute(RequestParameter.USER_LOCALE.getValue(), user.getLocale());
                    session.setAttribute(RequestParameter.ROLE.getValue(), user.getRole());
                    jspPageName = JspPageName.ADMIN_USER_PAGE;
                } catch (ServiceException e) {

                   logger.error(e);
                }
                break;
            }
        }
        if (!hasCookieLogin) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(RequestParameter.WELCOME_LOCALE.getValue())) {
                    request.getSession().setAttribute(RequestParameter.WELCOME_LOCALE.getValue(), cookie.getName());
                    break;
                }
            }
            jspPageName = JspPageName.WELCOME_PAGE;
        }
        return jspPageName;
    }
}
