package by.tr.hotelbooking.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Contract implements Identified{

    private int id;
    private String accountLogin;
    private Date dateIn;
    private Date dateOut;
    private BigDecimal totalPrice;
    private int hotelroomNumber;
    private boolean acceptStatus;

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

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getHotelroomNumber() {
        return hotelroomNumber;
    }

    public void setHotelroomNumber(int hotelroomNumber) {
        this.hotelroomNumber = hotelroomNumber;
    }

    public boolean isAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(boolean acceptStatus) {
        this.acceptStatus = acceptStatus;
    }
}
