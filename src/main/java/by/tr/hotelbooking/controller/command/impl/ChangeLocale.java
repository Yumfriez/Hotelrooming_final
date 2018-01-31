package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
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

    private ChangeLocale() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String locale = request.getParameter(RequestParameter.LOCALE.getValue());
        String login = (String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue());
        JspPageName page = JspPageName.valueOf((request.getParameter("page")).toUpperCase());

        try {
            Validator.checkIsNotEmpty(locale);
            if (login != null) {
                logger.debug(login + " try to change locale");
                UserService userService = ServiceFactory.getInstance().getUserService();
                userService.updateLocale(login, locale);

                request.getSession().setAttribute(RequestParameter.USER_LOCALE.getValue(), locale);
            }
            else {
                logger.debug("Unauthorized user try to change locale");
                changeLocaleWithCookies(request, response, locale);
            }
        } catch (ValidatorException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
        }
        catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        ForwarRedirectChooser.doForward(request,response,page.getPath());
    }

    private void changeLocaleWithCookies(HttpServletRequest request, HttpServletResponse response, String locale) throws ServiceException {
        Cookie cookies[] = request.getCookies();
        boolean hasCookie = false;
        logger.debug("Reading coockies...");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RequestParameter.LOGIN.getValue())) {
                hasCookie = true;

                UserService userService = ServiceFactory.getInstance().getUserService();
                userService.updateLocale(cookie.getValue(), locale);

                request.getSession().setAttribute(RequestParameter.USER_LOCALE.getValue(), locale);
                break;
            }
        }
        if (!hasCookie) {
            logger.debug("No coockies found.");
            request.getSession().setAttribute(RequestParameter.WELCOME_LOCALE.getValue(), locale);
            Cookie cookieWelcomeLocale = new Cookie(RequestParameter.WELCOME_LOCALE.getValue(), locale);
            response.addCookie(cookieWelcomeLocale);
        }
    }
}

