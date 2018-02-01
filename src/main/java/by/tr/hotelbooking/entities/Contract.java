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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;
        if (hotelroomNumber != contract.hotelroomNumber) return false;
        if (acceptStatus != contract.acceptStatus) return false;
        if (!accountLogin.equals(contract.accountLogin)) return false;
        if (!dateIn.equals(contract.dateIn)) return false;
        if (!dateOut.equals(contract.dateOut)) return false;
        return totalPrice.equals(contract.totalPrice);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountLogin.hashCode();
        result = 31 * result + dateIn.hashCode();
        result = 31 * result + dateOut.hashCode();
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + hotelroomNumber;
        result = 31 * result + (acceptStatus ? 1 : 0);
        return result;
    }
}
