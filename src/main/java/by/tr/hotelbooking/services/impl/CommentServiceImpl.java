package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.dao.impl.CommentDAO;
import by.tr.hotelbooking.entities.Comment;
import by.tr.hotelbooking.services.CommentService;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public class CommentServiceImpl implements CommentService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public void addComment(Date commentDate, String userLogin, String commentText) throws ServiceException {

        Comment comment = new Comment();
        comment.setAccountLogin(userLogin);
        comment.setCommentDate(commentDate);
        comment.setText(commentText);

        CommentDAO commentDAO = daoFactory.getCommentDAO();
        try {
            commentDAO.add(comment);
        } catch (DAOException e){
            throw new ServiceException(e);
        }


    }

    @Override
    public void removeComment(int commentId) throws ServiceException {

        CommentDAO commentDAO = daoFactory.getCommentDAO();
        try {
            commentDAO.delete(commentId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }


    }

    @Override
    public int getRecordsCount() throws ServiceException {
        int num = 0;
        try {
            num = daoFactory.getCommentDAO().getRecordsCount();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return num;
    }

    @Override
    public int getPageNumber(String stringPageValue) {

        int page;
        try {
            page = Integer.parseInt(stringPageValue);
        } catch (NumberFormatException e) {
            page = 1;
        }
        return page;
    }

    @Override
    public int getPagesCount(int recordsCount) throws ServiceException {
        return (int) Math.ceil(recordsCount * 1.0 / 10);
    }

    @Override
    public List<Comment> getCommentsForPage(int page) throws ServiceException {
        List<Comment> commentList;
        try {
            CommentDAO commentDAO = daoFactory.getCommentDAO();
            commentList = commentDAO.getRecordsWithOffset((page-1)*10,10);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return commentList;
    }
}
