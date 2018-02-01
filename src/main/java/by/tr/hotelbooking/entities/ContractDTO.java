package by.tr.hotelbooking.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class ContractDTO implements Identified {

    private int id;
    private String accountLogin;
    private Date dateIn;
    private Date dateOut;
    private BigDecimal totalPrice;
    private int hotelroomNumber;
    private int placesCount;
    private int floor;
    private RoomType roomType;
    private String imageName;

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

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractDTO that = (ContractDTO) o;

        if (id != that.id) return false;
        if (hotelroomNumber != that.hotelroomNumber) return false;
        if (placesCount != that.placesCount) return false;
        if (floor != that.floor) return false;
        if (!accountLogin.equals(that.accountLogin)) return false;
        if (!dateIn.equals(that.dateIn)) return false;
        if (!dateOut.equals(that.dateOut)) return false;
        if (!totalPrice.equals(that.totalPrice)) return false;
        if (!roomType.equals(that.roomType)) return false;
        return imageName.equals(that.imageName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountLogin.hashCode();
        result = 31 * result + dateIn.hashCode();
        result = 31 * result + dateOut.hashCode();
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + hotelroomNumber;
        result = 31 * result + placesCount;
        result = 31 * result + floor;
        result = 31 * result + roomType.hashCode();
        result = 31 * result + imageName.hashCode();
        return result;
    }
}
