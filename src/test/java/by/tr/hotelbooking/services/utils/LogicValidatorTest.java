package by.tr.hotelbooking.services.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.sql.Date;

@RunWith(DataProviderRunner.class)
public class LogicValidatorTest {

    @DataProvider
    public static Object[][] getWrongPricePairs() {
        return new Object[][]{
                {new BigDecimal("3"), new BigDecimal("2")},
                {new BigDecimal("12"), new BigDecimal("11.9")},
                {new BigDecimal("10.2"), new BigDecimal("5")},
                {new BigDecimal("12.0"), new BigDecimal("10")},
        };
    }

    @DataProvider
    public static Object[][] getCorrectPricePairs() {
        return new Object[][]{
                {new BigDecimal("12"), new BigDecimal("25")},
                {new BigDecimal("12"), new BigDecimal("123")},
                {new BigDecimal("17.25"), new BigDecimal("17.25")},
                {new BigDecimal("2366"), new BigDecimal("2366.2")},
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongPricePairs")
    public void shouldThrowExceptionWhenMinGreatThanMax(BigDecimal minValue, BigDecimal maxValue) throws LogicException {
        LogicValidator.checkMinAndMaxPrices(minValue, maxValue);
    }

    @Test
    @UseDataProvider("getCorrectPricePairs")
    public void shouldNotThrowExceptionWhenMinLessThanMax(BigDecimal minValue, BigDecimal maxValue) throws LogicException {
        LogicValidator.checkMinAndMaxPrices(minValue, maxValue);
    }

    @DataProvider
    public static Object[][] getWrongDates() {
        return new Object[][]{
                {new Date(new java.util.Date().getTime()-1000*60*60*24*2)},
                {new Date(new java.util.Date().getTime()-1000L*60*60*24*31*3)},
                {new Date(new java.util.Date().getTime()-1000L*60*60*24*31*12*2)}
        };
    }

    @DataProvider
    public static Object[][] getCorrectDates() {
        return new Object[][]{
                {new Date(new java.util.Date().getTime())},
                {new Date(new java.util.Date().getTime()+1000*60*60*24*2)},
                {new Date(new java.util.Date().getTime()+1000L*60*60*24*31*3)},
                {new Date(new java.util.Date().getTime()+1000L*60*60*24*31*12*2)}
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongDates")
    public void shouldThrowExceptionWhenDateInLessThanCurrentDate(Date date) throws LogicException{
        LogicValidator.checkDateIn(date);
    }
    @Test
    @UseDataProvider("getCorrectDates")
    public void shouldNotThrowExceptionWhenDateInLessThanCurrentDate(Date date) throws LogicException{
        LogicValidator.checkDateIn(date);
    }

    @DataProvider
    public static Object[][] getWrongPriceValues() {
        return new Object[][]{
                {new BigDecimal("0")},
                {new BigDecimal("0.0")},
                {new BigDecimal("-1")},
        };
    }

    @DataProvider
    public static Object[][] getCorrectPriceValues() {
        return new Object[][]{
                {new BigDecimal("12")},
                {new BigDecimal("0.1")},
                {new BigDecimal("17.25")}
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongPriceValues")
    public void shouldThrowExceptionWhenWrongPrice(BigDecimal wrongPrice) throws LogicException {
        LogicValidator.checkPrice(wrongPrice);
    }

    @Test
    @UseDataProvider("getCorrectPriceValues")
    public void shouldNotThrowExceptionWhenCorrectPrice(BigDecimal correctPrice) throws LogicException {
        LogicValidator.checkPrice(correctPrice);
    }

    @DataProvider
    public static Object[][] getWrongFloorValues() {
        return new Object[][]{
                {100},{0},{200}
        };
    }

    @DataProvider
    public static Object[][] getCorrectFloorValues() {
        return new Object[][]{
                {1},{5},{25}
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongFloorValues")
    public void shouldThrowExceptionWhenWrongFloor(int wrongFloor) throws LogicException {
        LogicValidator.checkHotelroomFloor(wrongFloor);
    }

    @Test
    @UseDataProvider("getCorrectFloorValues")
    public void shouldNotThrowExceptionWhenCorrectFloor(int correctFloor) throws LogicException {
        LogicValidator.checkHotelroomFloor(correctFloor);
    }
    @DataProvider
    public static Object[][] getWrongPlacesValues() {
        return new Object[][]{
                {10},{0},{125}
        };
    }

    @DataProvider
    public static Object[][] getCorrectPlacesValues() {
        return new Object[][]{
                {1},{5},{9}
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongPlacesValues")
    public void shouldThrowExceptionWhenWrongPlaces(int wrongPlaces) throws LogicException {
        LogicValidator.checkPlacesCount(wrongPlaces);
    }

    @Test
    @UseDataProvider("getCorrectPlacesValues")
    public void shouldNotThrowExceptionWhenCorrectPlaces(int correctPlaces) throws LogicException {
        LogicValidator.checkPlacesCount(correctPlaces);
    }
    @DataProvider
    public static Object[][] getWrongDaysCountValues() {
        return new Object[][]{
                {366},{0},{600}
        };
    }

    @DataProvider
    public static Object[][] getCorrectDaysCountValues() {
        return new Object[][]{
                {1},{60},{365}
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongDaysCountValues")
    public void shouldThrowExceptionWhenWrongDaysCount(int wrongDaysCount) throws LogicException {
        LogicValidator.checkDaysCount(wrongDaysCount);
    }

    @Test
    @UseDataProvider("getCorrectDaysCountValues")
    public void shouldNotThrowExceptionWhenCorrectDaysCount(int correctDaysCount) throws LogicException {
        LogicValidator.checkDaysCount(correctDaysCount);
    }

    @DataProvider
    public static Object[][] getWrongNumberValues() {
        return new Object[][]{
                {1000},{0},{25555}
        };
    }

    @DataProvider
    public static Object[][] getCorrectNumberValues() {
        return new Object[][]{
                {1},{5},{255}
        };
    }

    @Test(expected = LogicException.class)
    @UseDataProvider("getWrongNumberValues")
    public void shouldThrowExceptionWhenWrongNumber(int wrongNumber) throws LogicException {
        LogicValidator.checkHotelroomNumber(wrongNumber);
    }

    @Test
    @UseDataProvider("getCorrectNumberValues")
    public void shouldNotThrowExceptionWhenCorrectNumber(int correctNumber) throws LogicException {
        LogicValidator.checkHotelroomNumber(correctNumber);
    }
}