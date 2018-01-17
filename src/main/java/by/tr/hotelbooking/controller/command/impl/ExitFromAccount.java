package by.tr.hotelbooking.controller.command.impl;



import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExitFromAccount implements Command {

    private static Logger logger = Logger.getLogger(ExitFromAccount.class);

    private static final ExitFromAccount instance = new ExitFromAccount();

    private ExitFromAccount() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(request.getSession().getAttribute(RequestParameter.LOGIN.getValue()+" try to log out from account"));
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RequestParameter.LOGIN.getValue())) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                request.getSession().invalidate();
                logger.debug("Coockies removed");
            }

            if (cookie.getName().equals(RequestParameter.WELCOME_LOCALE.getValue())) {
                request.setAttribute(RequestParameter.WELCOME_LOCALE.getValue(),cookie.getValue());
            }
        }

        ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
        responseTypeChooser.doForward(request,response,JspPageName.WELCOME_PAGE.getPath());
        logger.debug("User logged out");
    }
}
