package by.tr.hotelbooking.entities;

import java.sql.Date;

public class Order implements Identified{

    private Integer id;
    private String accountLogin;
    private int preferedPlacesCount;
    private Date preferedDateIn;
    private int daysCount;
    private RoomType roomType;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public int getPreferedPlacesCount() {
        return preferedPlacesCount;
    }

    public void setPreferedPlacesCount(int preferedPlacesCount) {
        this.preferedPlacesCount = preferedPlacesCount;
    }

    public Date getPreferedDateIn() {
        return preferedDateIn;
    }

    public void setPreferedDateIn(Date preferedDateIn) {
        this.preferedDateIn = preferedDateIn;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
