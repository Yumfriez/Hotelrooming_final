package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

        String commentText = request.getParameter(RequestParameter.COMMENT_TEXT.getValue());
        String commentDateString = request.getParameter(RequestParameter.COMMENT_DATE.getValue());
        String login = request.getParameter(RequestParameter.LOGIN.getValue());
        try{
            Validator.checkIsNotEmpty(commentText, commentDateString, login);
            Validator.checkIsValidLogin(login);
            Date dateIn = StringParser.parseFromStringToDate(commentDateString);
            CommentService commentService = ServiceFactory.getInstance().getCommentService();
            commentService.addComment(dateIn, login, commentText);
            ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
            responseTypeChooser.doRedirect(response,"hotelrooming?command=show_comments");
        } catch (ParseException | ServiceException | ValidatorException e) {
           logger.error(e+e.getMessage());
        }

    }
}
