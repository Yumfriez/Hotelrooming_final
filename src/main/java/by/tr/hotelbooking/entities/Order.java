package by.tr.hotelbooking.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Order implements Identified{

    private Integer id;
    private String accountLogin;
    private int preferedPlacesCount;
    private Date preferedDateIn;
    private int daysCount;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (preferedPlacesCount != order.preferedPlacesCount) return false;
        if (daysCount != order.daysCount) return false;
        if (!id.equals(order.id)) return false;
        if (!accountLogin.equals(order.accountLogin)) return false;
        if (!preferedDateIn.equals(order.preferedDateIn)) return false;
        if (!minPrice.equals(order.minPrice)) return false;
        if (!maxPrice.equals(order.maxPrice)) return false;
        return roomType.equals(order.roomType);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + accountLogin.hashCode();
        result = 31 * result + preferedPlacesCount;
        result = 31 * result + preferedDateIn.hashCode();
        result = 31 * result + daysCount;
        result = 31 * result + minPrice.hashCode();
        result = 31 * result + maxPrice.hashCode();
        result = 31 * result + roomType.hashCode();
        return result;
    }
}
