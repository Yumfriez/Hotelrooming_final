package by.tr.hotelbooking.controller.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class ValidatorTest {

    @DataProvider
    public static Object[][] getWrongNumberValues(){
        return new Object[][] {
                {"asd", ""},
                {"12sd", " 123", "23 "},
                {"1 2"},
                {"12."},
                {"12.3", "12d"},
                {"23  "},
        };
    }

    @DataProvider
    public static Object[][] getCorrectNumberValues(){
        return new Object[][] {
                {"12", "25"},
                {"12", "123", "23"},
                {"1"},
                {"2366"},
                {"63", "126636"},
                {"0"},
        };
    }

    @Test (expected = ValidatorException.class)
    @UseDataProvider("getWrongNumberValues")
    public void shouldThrowExceptionWhenWrongNumberFormat(String... values) throws ValidatorException {
        Validator.checkIsValidNumbers(values);
    }

    @Test
    @UseDataProvider("getCorrectNumberValues")
    public void shouldNotThrowExceptionWhenCorrectNumberFormat(String... values) throws ValidatorException{
        Validator.checkIsValidNumbers(values);
    }

    @DataProvider
    public static Object[][] getWrongEmailValues(){
        return new Object[][] {
                {"asd"}, {"asd@"}, {"asd@mail..ru"}, {"@mail.ru"}, {"asdasd123  asd@mail.ru"}, {"budaev@mail."}
        };
    }

    @DataProvider
    public static Object[][] getCorrectEmailValues(){
        return new Object[][] {
                {"hhh12@mail.ru", "budaev-vanya@mail.ru"}, {"team-lid@example.com.gr", "ivan.budaev_1998@gmail.com"},
                {"hei-you@example.com.check"}
        };
    }


    @Test (expected = ValidatorException.class)
    @UseDataProvider("getWrongEmailValues")
    public void shouldThrowExceptionWhenWrongEmailFormat(String... values) throws ValidatorException {
        Validator.checkIsValidEmail(values);
    }

    @Test
    @UseDataProvider("getCorrectEmailValues")
    public void shouldNotThrowExceptionWhenCorrectEmailFormat(String... values) throws ValidatorException{
        Validator.checkIsValidEmail(values);
    }

    @DataProvider
    public static Object[][] getWrongPriceValues(){
        return new Object[][] {
                {"asd", ""},
                {"12sd", " 123", "23 "},
                {"1.3 s2"},
                {"12."},
                {"12.3", "12 d"},
                {"23. 235"},
        };
    }

    @DataProvider
    public static Object[][] getCorrectPriceValues(){
        return new Object[][] {
                {"12.45", "2553.3"},
                {"12.0", "1232323", "23.75475"},
                {"1.6437"},
                {"2366.12513"},
                {"63", "126.636"},
                {"0"},
        };
    }


    @Test (expected = ValidatorException.class)
    @UseDataProvider("getWrongPriceValues")
    public void shouldThrowExceptionWhenWrongPriceFormat(String... values) throws ValidatorException {
        Validator.checkIsValidPrice(values);
    }

    @Test
    @UseDataProvider("getCorrectPriceValues")
    public void shouldNotThrowExceptionWhenCorrectPriceFormat(String... values) throws ValidatorException{
        Validator.checkIsValidPrice(values);
    }

    @DataProvider
    public static Object[][] getWrongLoginPasswordValues(){
        return new Object[][] {
                {"asd"}, {"12sd"},
                {" 123"}, {"23 "},
                {"12Vanya"}, {"Vanya."},
                {" Vanya"}, {"Kilasd "},
                {" Hello"},
        };
    }

    @DataProvider
    public static Object[][] getCorrectLoginPasswordValues(){
        return new Object[][] {
                {"Canelo"}, {"fable"},
                {"Magic"}, {"Alvarez"}, {"Vanya"},
                {"Vanyadas2513636"},
                {"vasdasd126234"},
                {"asd678345"}, {"sfdssd65"},
                {"asdasd237247"},
        };
    }



    @Test (expected = ValidatorException.class)
    @UseDataProvider("getWrongLoginPasswordValues")
    public void shouldThrowExceptionWhenWrongLoginFormat(String login) throws ValidatorException {
        Validator.checkIsValidLogin(login);
    }

    @Test
    @UseDataProvider("getCorrectLoginPasswordValues")
    public void shouldNotThrowExceptionWhenCorrectLoginFormat(String login) throws ValidatorException{
        Validator.checkIsValidLogin(login);
    }

    @Test (expected = ValidatorException.class)
    @UseDataProvider("getWrongLoginPasswordValues")
    public void shouldThrowExceptionWhenWrongPasswordFormat(String password) throws ValidatorException {
        Validator.checkIsValidPassword(password);
    }

    @Test
    @UseDataProvider("getCorrectLoginPasswordValues")
    public void shouldNotThrowExceptionWhenCorrectPasswordFormat(String password) throws ValidatorException{
        Validator.checkIsValidPassword(password);
    }
}
