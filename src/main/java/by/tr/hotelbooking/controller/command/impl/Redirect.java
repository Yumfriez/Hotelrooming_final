package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
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

    private Redirect() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Attempt to restore session");
        Object role = request.getSession().getAttribute(RequestParameter.ROLE.getValue());
        JspPageName jspPageName;
        if (role != null) {
            jspPageName = JspPageName.ADMIN_USER_PAGE;
        } else {
            jspPageName = readCookies(request);
        }

        ForwarRedirectChooser.doForward(request,response,jspPageName.getPath());
    }

    private JspPageName readCookies(HttpServletRequest request) {
        logger.debug("Reading data from coockies...");
        JspPageName jspPageName = JspPageName.WELCOME_PAGE;
        Cookie cookies[] = request.getCookies();
        boolean hasCookieLogin = false;
        String loginAttributeName = RequestParameter.LOGIN.getValue();
        for(Cookie cookie: cookies){
            if (loginAttributeName.equals(cookie.getName())) {
                hasCookieLogin = true;
                try {
                    fillSessionAttributes(request, cookie);
                    jspPageName = JspPageName.ADMIN_USER_PAGE;

                } catch (ServiceException e) {
                    logger.error(e);
                    hasCookieLogin = false;
                    request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
                }
                break;
            }
        }
        if (!hasCookieLogin) {
            setWelcomePageLocale(request, cookies);
        }
        return jspPageName;
    }

    private void fillSessionAttributes(HttpServletRequest request, Cookie cookie) throws ServiceException{
        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = userService.getUserByLogin(cookie.getValue());
        HttpSession session = request.getSession();
        session.setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
        session.setAttribute(RequestParameter.USER_LOCALE.getValue(), user.getLocale());
        session.setAttribute(RequestParameter.ROLE.getValue(), user.getRole());
    }

    private void setWelcomePageLocale(HttpServletRequest request,Cookie[] cookies){
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RequestParameter.WELCOME_LOCALE.getValue())) {
                request.getSession().setAttribute(RequestParameter.WELCOME_LOCALE.getValue(), cookie.getValue());
                break;
            }
        }
    }
}
