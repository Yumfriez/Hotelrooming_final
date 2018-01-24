package by.tr.hotelbooking.services.utils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

public class LogicValidator {
    private LogicValidator(){

    }

    public static void checkMinAndMaxPrices(BigDecimal minPrice, BigDecimal maxPrice) throws LogicException{
        if(minPrice.compareTo(maxPrice)>0){
            throw new LogicException("Minimum price should be less than maximum price.");
        }
    }

    public static void checkDateIn(Date dateIn) throws LogicException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        int dayIn = calendar.get(Calendar.DAY_OF_MONTH);
        int monthIn = calendar.get(Calendar.MONTH);
        int yearIn = calendar.get(Calendar.YEAR);
        Date currentDate = new Date(new java.util.Date().getTime());
        calendar.setTime(currentDate);
        int dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        int monthCurrent = calendar.get(Calendar.MONTH);
        int yearCurrent = calendar.get(Calendar.YEAR);
        if(yearIn>yearCurrent){
            return;
        }
        if(yearIn==yearCurrent){
            if(monthIn>monthCurrent){
                return;
            }
            if(monthIn==monthCurrent){
                if(dayIn<dayCurrent){
                    throw new LogicException("Wrong date. Day of arriving should be not less than today's.");
                }
            }else{
                throw new LogicException("Wrong date. Month of arriving should be not less than current.");
            }
        }else{
            throw new LogicException("Wrong date. Year of arriving should be not less than current.");
        }


    }

    public static void checkPlacesCount(int placesCount) throws LogicException{
        if(placesCount<1 || placesCount>9){
            throw new LogicException("Hotelroom should have from 1 to 9 places.");
        }

    }

    public static void checkDaysCount(int daysCount) throws LogicException{
        if(daysCount<1 || daysCount>365){
            throw new LogicException("You can order hotelroom from 1 to 365 days.");
        }

    }

    public static void checkHotelroomNumber(int hotelroomNumber) throws LogicException{
        if(hotelroomNumber<1 || hotelroomNumber>999){
            throw new LogicException("Hotelroom number should be from 1 to 999.");
        }

    }

    public static void checkHotelroomFloor(int floor) throws LogicException{
        if(floor<1 || floor>99){
            throw new LogicException("Hotelroom floor should be from 1 to 99.");
        }
    }

    public static void checkPrice(BigDecimal price) throws LogicException{
        if(price.compareTo(new BigDecimal("0"))<=0){
            throw new LogicException("Price should be greater than 0.");
        }
    }
}
