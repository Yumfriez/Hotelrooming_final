package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.CommentService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class AddCommentCommand implements Command {

    private static Logger logger = Logger.getLogger(AddCommentCommand.class);

    private final static AddCommentCommand instance = new AddCommentCommand();

    private AddCommentCommand(){

    }

    public static AddCommentCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String commentText = request.getParameter(RequestParameter.COMMENT_TEXT.getValue());
        String login = request.getParameter(RequestParameter.LOGIN.getValue());

        try{
            Validator.checkIsNotEmpty(commentText, login);
            Validator.checkIsValidLogin(login);
            Date date = new Date(new java.util.Date().getTime());
            CommentService commentService = ServiceFactory.getInstance().getCommentService();
            commentService.addComment(date, login, commentText);
            ForwarRedirectChooser.doRedirect(response, servletPath, RequestCommandParameter.SHOW_COMMENTS);

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
