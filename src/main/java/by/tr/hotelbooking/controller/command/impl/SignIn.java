package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.entities.User;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SignIn implements Command {
    private static Logger logger = Logger.getLogger(SignIn.class);

    private static SignIn instance = new SignIn();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private SignIn() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String login = request.getParameter(RequestParameter.LOGIN.getValue());
        String password = request.getParameter(RequestParameter.PASSWORD.getValue());
        try {
            Validator.checkIsNotEmpty(login, password);
            Validator.checkIsValidLogin(login);
            Validator.checkIsValidPassword(password);
            User user = serviceFactory.getUserService().signIn(login, password);
            if (!user.getIsBlocked()) {
                if (user.getRole() != null) {
                    Cookie cookieLogin = new Cookie(RequestParameter.LOGIN.getValue(), user.getLogin());
                    response.addCookie(cookieLogin);
                    HttpSession session = request.getSession(true);
                    session.setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
                    session.setAttribute(RequestParameter.ROLE.getValue(), String.valueOf(user.getRole()));
                    session.setAttribute(RequestParameter.PAGE.getValue(), JspPageName.ADMIN_USER_PAGE);
                    session.setAttribute(RequestParameter.USER_LOCALE.getValue(), user.getLocale());
                    ForwarRedirectChooser.doRedirect(response,servletPath, RequestCommandParameter.REDIRECT);
                } else {
                    request.setAttribute(RequestParameter.INFORMATION.getValue(), "error of user identifying");
                    ForwarRedirectChooser.doForward(request, response, JspPageName.WELCOME_PAGE.getPath());
                }
            } else {
                request.setAttribute(RequestParameter.INFORMATION.getValue(), "account is blocked");
                ForwarRedirectChooser.doForward(request, response, JspPageName.WELCOME_PAGE.getPath());

            }
        } catch (ValidatorException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ForwarRedirectChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "Wrong login or password");
            ForwarRedirectChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
        }


    }
}
