package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
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

        String stringCommentId = request.getParameter(RequestParameter.COMMENT_ID.getValue());

        CommentService commentService = ServiceFactory.getInstance().getCommentService();

        try {
            Validator.checkIsNotEmpty(stringCommentId);
            Validator.checkIsValidNumbers(stringCommentId);

            int commentId = StringParser.parseFromStringToInt(stringCommentId);

            commentService.removeComment(commentId);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response,"hotelrooming?command=show_comments");
        }catch (ValidatorException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.COMMENTS_PAGE.getPath());
        }
        catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doForward(request, response, JspPageName.COMMENTS_PAGE.getPath());
        }


    }
}
