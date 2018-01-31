package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.UserService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockUserCommand implements Command {
    private static Logger logger = Logger.getLogger(BlockUserCommand.class);

    private final static BlockUserCommand instance = new BlockUserCommand();

    private BlockUserCommand(){

    }

    public static BlockUserCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String userLogin = request.getParameter(RequestParameter.LOGIN.getValue());

        try {
            Validator.checkIsNotEmpty(userLogin);
            Validator.checkIsValidLogin(userLogin);

            UserService userService = ServiceFactory.getInstance().getUserService();
            userService.changeBlockStatus(userLogin, true);

            ForwarRedirectChooser.doRedirect(response, servletPath, RequestCommandParameter.SHOW_USERS);

        } catch (ValidatorException e) {
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        } catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
        }
    }
}
