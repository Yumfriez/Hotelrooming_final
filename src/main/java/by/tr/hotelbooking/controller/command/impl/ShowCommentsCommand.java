package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.ForwarRedirectChooser;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.RequestCommandParameter;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import by.tr.hotelbooking.entities.Comment;
import by.tr.hotelbooking.services.CommentService;
import by.tr.hotelbooking.services.exception.ServiceException;
import by.tr.hotelbooking.services.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCommentsCommand implements Command {

    private static Logger logger = Logger.getLogger(ShowCommentsCommand.class);

    private final static ShowCommentsCommand instance = new ShowCommentsCommand();

    public static ShowCommentsCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageStringValue = request.getParameter(RequestParameter.PAGINATION.getValue());
        Object role = request.getSession().getAttribute(RequestParameter.ROLE.getValue());

        try {
            CommentService commentService = ServiceFactory.getInstance().getCommentService();
            int recordsCount = commentService.getRecordsCount();
            int pagesCount = commentService.getPagesCount(recordsCount);
            int pageNumber = commentService.getPageNumber(pageStringValue);
            List<Comment> commentsList = commentService.getCommentsForPage(pageNumber);

            request.setAttribute(RequestParameter.COMMENTS_LIST.getValue(), commentsList);
            request.setAttribute(RequestParameter.PAGES_COUNT.getValue(), pagesCount);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER.getValue(), pageNumber);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestCommandParameter.SHOW_COMMENTS.getValue());

            ForwarRedirectChooser.doForward(request, response, JspPageName.COMMENTS_PAGE.getPath());
        }catch (ServiceException e){
            logger.error(e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            if(null!=role){
                ForwarRedirectChooser.doForward(request, response, JspPageName.ADMIN_USER_PAGE.getPath());
            }else{
                ForwarRedirectChooser.doForward(request, response, JspPageName.WELCOME_PAGE.getPath());
            }
        }
    }
}
