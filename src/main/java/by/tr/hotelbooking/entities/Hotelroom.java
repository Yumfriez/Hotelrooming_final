package by.tr.hotelbooking.entities;

import java.math.BigDecimal;

public class Hotelroom implements Identified{

    private Integer id;
    private int number;
    private int placesCount;
    private int floor;
    private BigDecimal dailyPrice;
    private RoomType roomType;
    private String imageName;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
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

        Hotelroom hotelroom = (Hotelroom) o;

        if (number != hotelroom.number) return false;
        if (placesCount != hotelroom.placesCount) return false;
        if (floor != hotelroom.floor) return false;
        if (!id.equals(hotelroom.id)) return false;
        if (!dailyPrice.equals(hotelroom.dailyPrice)) return false;
        if (!roomType.equals(hotelroom.roomType)) return false;
        return imageName.equals(hotelroom.imageName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + number;
        result = 31 * result + placesCount;
        result = 31 * result + floor;
        result = 31 * result + dailyPrice.hashCode();
        result = 31 * result + roomType.hashCode();
        result = 31 * result + imageName.hashCode();
        return result;
    }
}
