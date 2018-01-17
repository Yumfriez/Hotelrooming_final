package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractJDBCDao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.entities.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CommentDAO extends AbstractJDBCDao<Comment> {

    private final static String GET_ALL_COMMENTS = "SELECT account.login, comment.comment_id, comment.text, "+
            "comment.c_date FROM hotelrooms.account JOIN hotelrooms.comment ON hotelrooms.comment.u_id = "+
            "hotelrooms.account.id WHERE comment.text IS NOT NULL";
    private static final String REMOVE_COMMENT = "DELETE FROM hotelrooms.comment WHERE comment_id=?";
    private static final String ADD_COMMENT = "INSERT INTO hotelrooms.comment (c_date, text, u_id) "+
            "VALUES (?, ?,(SELECT id FROM account WHERE login=?))";
    private static final String GET_LAST_ID = "SELECT comment.comment_id FROM hotelrooms.comment ORDER BY comment.comment_id desc limit 1;";

    public CommentDAO() {

    }

    @Override
    protected String getSelectQuery() {
        return GET_ALL_COMMENTS;
    }

    @Override
    protected String getSelectByPKQuery() {
        return null;
    }

    @Override
    protected String getAddQuery() {
        return ADD_COMMENT;
    }

    @Override
    protected String getUpdateQuery() {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return REMOVE_COMMENT;
    }

    @Override
    protected String getLastAdded() {
        return GET_LAST_ID;
    }

    @Override
    protected List<Comment> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Comment object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Comment object) throws DAOException {

    }
}
