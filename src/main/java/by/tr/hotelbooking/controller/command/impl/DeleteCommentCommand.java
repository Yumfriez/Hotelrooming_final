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

public class DeleteCommentCommand implements Command {

    private static Logger logger = Logger.getLogger(DeleteCommentCommand.class);

    private final static DeleteCommentCommand instance = new DeleteCommentCommand();

    private DeleteCommentCommand(){

    }

    public static DeleteCommentCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String stringCommentId = request.getParameter(RequestParameter.COMMENT_ID.getValue());

        try {
            Validator.checkIsNotEmpty(stringCommentId);
            Validator.checkIsValidNumbers(stringCommentId);

            int commentId = StringParser.parseFromStringToInt(stringCommentId);

            CommentService commentService = ServiceFactory.getInstance().getCommentService();
            commentService.removeComment(commentId);

            ForwarRedirectChooser.doRedirect(response,servletPath, RequestCommandParameter.SHOW_COMMENTS);

        }catch (ValidatorException e){
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
