package by.tr.hotelbooking.entities;

import java.sql.Date;

public class Comment implements Identified{

    private int id;
    private String accountLogin;
    private String userName;
    private String userSurname;
    private Date commentDate;
    private String text;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (!accountLogin.equals(comment.accountLogin)) return false;
        if (!userName.equals(comment.userName)) return false;
        if (!userSurname.equals(comment.userSurname)) return false;
        if (!commentDate.equals(comment.commentDate)) return false;
        return text.equals(comment.text);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountLogin.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + userSurname.hashCode();
        result = 31 * result + commentDate.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }
}
