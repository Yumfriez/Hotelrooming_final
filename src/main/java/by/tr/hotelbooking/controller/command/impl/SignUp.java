package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
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
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private SignUp() {
    }

    public static Command getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        try {

            String login = request.getParameter(RequestParameter.LOGIN.getValue());
            String password = request.getParameter(RequestParameter.PASSWORD.getValue());
            String email = request.getParameter(RequestParameter.EMAIL.getValue());
            String name = request.getParameter(RequestParameter.NAME.getValue());
            String lastName = request.getParameter(RequestParameter.LASTNAME.getValue());
            //TODO VALIDATOR FOR NAME AND LASTNAME
            Validator.checkIsNotEmpty(login, password);
            Validator.checkIsValidLogin(login);
            Validator.checkIsValidPassword(password);
            Validator.checkIsValidEmail(email);

            UserService userService = serviceFactory.getUserService();
            User checkedUser = userService.getUserByLogin(login);
            if (checkedUser==null){
                user = userService.signUp(login, password, name, lastName, email, request.getLocale().toString());

                HttpSession session = request.getSession(true);
                session.setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
                session.setAttribute(RequestParameter.ROLE.getValue(), RequestParameter.USER.getValue());
                Cookie cookie = new Cookie(RequestParameter.LOGIN.getValue(), user.getLogin());

                response.addCookie(cookie);
                session.setAttribute(RequestParameter.USER_LOCALE.getValue(), user.getLocale());
                session.setAttribute(RequestParameter.PAGE.getValue(), JspPageName.ADMIN_USER_PAGE);
                ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
                responseTypeChooser.doRedirect(response, "hotelrooming?command=after_sign_up");
            } else{
                request.setAttribute(RequestParameter.INFORMATION.getValue(), "Account is already exists");
                ResponseTypeChooser responseTypeChooser=new ResponseTypeChooser();
                responseTypeChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
            }

        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ResponseTypeChooser responseTypeChooser=new ResponseTypeChooser();
            responseTypeChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
        } catch (ValidatorException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ResponseTypeChooser responseTypeChooser=new ResponseTypeChooser();
            responseTypeChooser.doForward(request,response, JspPageName.WELCOME_PAGE.getPath());
        }

    }
}
