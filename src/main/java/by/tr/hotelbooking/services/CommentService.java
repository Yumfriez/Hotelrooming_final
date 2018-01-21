package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.Comment;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface CommentService {

    void addComment(Date commentDate, String userLogin, String commentText) throws ServiceException;
    void removeComment(int commentId) throws ServiceException;
    int getRecordsCount() throws ServiceException;
    int getPageNumber(String stringPageValue);
    int getPagesCount(int recordsCount) throws ServiceException;
    List<Comment> getCommentsForPage(int page) throws ServiceException;

}
