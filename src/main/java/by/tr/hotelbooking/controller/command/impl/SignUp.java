package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.entities.User;
import by.tr.hotelbooking.services.UserService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignUp implements Command {
    private static Logger logger = Logger.getLogger(SignUp.class);

    private static final SignUp instance = new SignUp();

    private SignUp() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String servletPath = request.getServletPath();
        String login = request.getParameter(RequestParameter.LOGIN.getValue());
        String password = request.getParameter(RequestParameter.PASSWORD.getValue());
        String email = request.getParameter(RequestParameter.EMAIL.getValue());
        String name = request.getParameter(RequestParameter.NAME.getValue());
        String lastName = request.getParameter(RequestParameter.LASTNAME.getValue());
        try {
            Validator.checkIsNotEmpty(login, password, email, name, lastName);
            Validator.checkIsValidLogin(login);
            Validator.checkIsValidPassword(password);
            Validator.checkIsValidEmail(email);
            Validator.checkIsValidName(name, lastName);

            UserService userService = ServiceFactory.getInstance().getUserService();
            User checkedUser = userService.getUserByLogin(login);

            if (checkedUser==null){
                User user = userService.signUp(login, password, name, lastName, email, request.getLocale().toString());

                HttpSession session = request.getSession(true);
                session.setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
                session.setAttribute(RequestParameter.ROLE.getValue(), RequestParameter.USER.getValue());
                Cookie cookie = new Cookie(RequestParameter.LOGIN.getValue(), user.getLogin());

                response.addCookie(cookie);
                session.setAttribute(RequestParameter.USER_LOCALE.getValue(), user.getLocale());
                session.setAttribute(RequestParameter.PAGE.getValue(), JspPageName.ADMIN_USER_PAGE);
                ForwarRedirectChooser.doRedirect(response,servletPath, RequestCommandParameter.REDIRECT);
            } else{
                request.setAttribute(RequestParameter.INFORMATION.getValue(), "Account is already exists");
                ForwarRedirectChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
            }

        } catch (ValidatorException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ForwarRedirectChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
        }

    }
}
