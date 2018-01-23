package by.tr.hotelbooking.controller.utils;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringParser {

    private final static String DATE_FORMAT = "yyyy-MM-dd";

    private StringParser(){

    }

    public static int parseFromStringToInt(String stringValue){
        return Integer.parseInt(stringValue);
    }

    public static Date parseFromStringToDate(String stringValue) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        java.util.Date dateValue = dateFormat.parse(stringValue);
        return new Date(dateValue.getTime());
    }

    public static BigDecimal parseFromStringToBigDecimal(String stringValue){
        return new BigDecimal(stringValue);
    }
}
