package by.tr.hotelbooking.entities;

import java.sql.Date;

public class Comment implements Identified{

    private int id;
    private String accountLogin;
    private Date commentDate;
    private String text;

    @Override
    public Integer getId() {
        return id;
    }
}
