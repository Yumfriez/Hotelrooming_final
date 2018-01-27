package by.tr.hotelbooking.dao.impl;

import by.tr.hotelbooking.dao.AbstractPaginalDao;
import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.entities.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends AbstractPaginalDao<Comment> {

    private final static String GET_ALL_COMMENTS = "SELECT account.login, comment.comment_id, comment.text, "+
            "comment.c_date FROM hotelrooms.account JOIN hotelrooms.comment ON hotelrooms.comment.u_id = "+
            "hotelrooms.account.id WHERE comment.text IS NOT NULL";
    private final static String GET_COMMENTS_FOR_PAGE = "SELECT account.login, account.name, account.surname," +
            " comment.comment_id, comment.text, comment.c_date FROM hotelrooms.account " +
            " JOIN hotelrooms.comment ON hotelrooms.comment.u_id = hotelrooms.account.u_id GROUP BY comment.comment_id LIMIT ?,?";
    private static final String REMOVE_COMMENT = "DELETE FROM hotelrooms.comment WHERE comment_id=?";
    private static final String ADD_COMMENT = "INSERT INTO hotelrooms.comment (c_date, text, u_id) "+
            "VALUES (?, ?,(SELECT u_id FROM account WHERE login=?))";

    private static final String GET_COUNT = "SELECT count(*) from hotelrooms.comment";


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
    protected String getRecordsWithOffsetQuery() {
        return GET_COMMENTS_FOR_PAGE;
    }

    @Override
    protected String getRecordsCountQuery() {
        return GET_COUNT;
    }

    @Override
    protected List<Comment> parseResultSet(ResultSet resultSet) throws DAOException {
        List<Comment> commentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Comment comment = new Comment();
                setCommentParameters(comment, resultSet);
                commentList.add(comment);
            }
        } catch (SQLException e){
            throw new DAOException("Parsing coomments from result to List error." + e);
        }
        return commentList;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Comment object) throws DAOException {

        try {
            statement.setDate(1, object.getCommentDate());
            statement.setString(2, object.getText());
            statement.setString(3, object.getAccountLogin());
        } catch (SQLException e){
            throw new DAOException(e);
        }

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Comment object) throws DAOException {

    }

    private void setCommentParameters(Comment comment, ResultSet resultSet) throws DAOException {

        try {
            comment.setId(resultSet.getInt("comment_id"));
            comment.setAccountLogin(resultSet.getString("login"));
            comment.setCommentDate(resultSet.getDate("c_date"));
            comment.setText(resultSet.getString("text"));
            comment.setUserName(resultSet.getString("name"));
            comment.setUserSurname(resultSet.getString("surname"));
        } catch (SQLException e){
            throw new DAOException(e);
        }

    }

}
