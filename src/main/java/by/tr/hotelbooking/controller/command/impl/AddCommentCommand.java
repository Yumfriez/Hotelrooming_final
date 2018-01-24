package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.utils.StringParser;
import by.tr.hotelbooking.controller.utils.Validator;
import by.tr.hotelbooking.controller.utils.ValidatorException;
import by.tr.hotelbooking.services.CommentService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;

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
        String commentDateString = request.getParameter(RequestParameter.COMMENT_DATE.getValue());
        String login = request.getParameter(RequestParameter.LOGIN.getValue());

        try{
            Validator.checkIsNotEmpty(commentText, commentDateString, login);
            Validator.checkIsValidLogin(login);
            Date dateIn = StringParser.parseFromStringToDate(commentDateString);
            CommentService commentService = ServiceFactory.getInstance().getCommentService();
            commentService.addComment(dateIn, login, commentText);
            ForwarRedirectChooser.doRedirect(response, servletPath, RequestCommandParameter.SHOW_COMMENTS);

        } catch (ParseException |  ValidatorException e) {
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
