package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.services.UserService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocale implements Command {

    private static Logger logger = Logger.getLogger(ChangeLocale.class);

    private static final ChangeLocale instance = new ChangeLocale();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private ChangeLocale() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = serviceFactory.getUserService();
        String locale = request.getParameter(RequestParameter.LOCALE.getValue());
        String login = (String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue());
        JspPageName page = JspPageName.valueOf((request.getParameter("page")).toUpperCase());

        try {
            if (login != null) {
                logger.debug(login + " try to change locale");
                userService.updateLocale(login, locale);
                request.getSession().setAttribute(RequestParameter.USER_LOCALE.getValue(), locale);
            }
            else {
                logger.debug("Unauthorized user try to change locale");
                changeLocaleWithCookies(request, response, locale, page);
            }
        } catch (ServiceException e) {
            logger.error(e);
        }
        ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
        responseTypeChooser.doForward(request,response,page.getPath());
    }

    private void changeLocaleWithCookies(HttpServletRequest request, HttpServletResponse response, String locale, JspPageName page) throws ServiceException {
        UserService IUserService = serviceFactory.getUserService();
        Cookie cookies[] = request.getCookies();
        boolean hasCookie = false;
        logger.debug("Reading coockies...");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RequestParameter.LOGIN.getValue())) {
                hasCookie = true;
                IUserService.updateLocale(cookie.getValue(), locale);
                request.getSession().setAttribute(RequestParameter.USER_LOCALE.getValue(), locale);
                break;
            }
        }
        if (!hasCookie) {
            logger.debug("No coockies found.");
            request.getSession().setAttribute(RequestParameter.WELCOME_LOCALE.getValue(), locale);
            Cookie cookieWelcomeLocale = new Cookie(RequestParameter.WELCOME_LOCALE.getValue(), locale);
            response.addCookie(cookieWelcomeLocale);
            page = JspPageName.WELCOME_PAGE;
        }
    }
}

