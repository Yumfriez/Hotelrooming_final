package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
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

        int commentId = Integer.parseInt(stringCommentId);

        try {
            commentService.removeComment(commentId);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response, "hotelrooming?command=show_comments");
        }catch (ServiceException e){
            logger.error(e);
        }

    }
}
