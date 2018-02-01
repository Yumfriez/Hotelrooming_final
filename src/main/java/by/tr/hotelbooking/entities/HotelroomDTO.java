package by.tr.hotelbooking.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class HotelroomDTO implements Identified{

    private Integer id;
    private int placesCount;
    private RoomType roomType;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Date dateIn;
    private Date dateOut;

    public HotelroomDTO(){

    }

    @Override
    public Integer getId() {
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelroomDTO that = (HotelroomDTO) o;

        if (placesCount != that.placesCount) return false;
        if (!id.equals(that.id)) return false;
        if (!roomType.equals(that.roomType)) return false;
        if (!minPrice.equals(that.minPrice)) return false;
        if (!maxPrice.equals(that.maxPrice)) return false;
        if (!dateIn.equals(that.dateIn)) return false;
        return dateOut.equals(that.dateOut);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + placesCount;
        result = 31 * result + roomType.hashCode();
        result = 31 * result + minPrice.hashCode();
        result = 31 * result + maxPrice.hashCode();
        result = 31 * result + dateIn.hashCode();
        result = 31 * result + dateOut.hashCode();
        return result;
    }
}
