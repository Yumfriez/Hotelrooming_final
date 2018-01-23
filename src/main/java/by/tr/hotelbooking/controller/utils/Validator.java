package by.tr.hotelbooking.controller.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private final static String LOGIN_PASSWORD_PATTERN = "^[A-Za-z]\\w{4,}$";
    private final static String PRICE_PATTERN = "^[0-9]+(\\.[0-9]+)?$";
    private final static String NUMBER_PATTERN = "^[0-9]+$";
    private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Validator(){

    }

    public static void checkIsNotEmpty(String...args) throws ValidatorException{
        if(null==args || args.length==0){
            throw new ValidatorException("Empty array of arguments for check");
        }
        int length = args.length;
        int index=0;
        while (index<length){
            if(null==args[index] || args[index].isEmpty()){
                throw new ValidatorException("Wrong input: empty string");
            }
            index++;
        }

    }

    public static void checkIsValidLogin(String login) throws ValidatorException{
        try {
            checkStringsByPattern(LOGIN_PASSWORD_PATTERN, login);
        }catch (ValidatorException e){
            throw new ValidatorException(e.getMessage()+" wrong login format");
        }
    }

    public static void checkIsValidPassword(String password) throws ValidatorException{
        try {
            checkStringsByPattern(LOGIN_PASSWORD_PATTERN, password);
        }catch (ValidatorException e){
            throw new ValidatorException(e.getMessage()+" wrong password format");
        }
    }

    public static void checkIsValidPrice(String...args) throws ValidatorException{
        try {
            checkStringsByPattern(PRICE_PATTERN, args);
        } catch (ValidatorException e){
            throw new ValidatorException(e.getMessage()+" wrong price format");
        }

    }

    public static void checkIsValidNumbers(String...args) throws ValidatorException{
        try {
            checkStringsByPattern(NUMBER_PATTERN, args);
        } catch (ValidatorException e){
            throw new ValidatorException(e.getMessage()+" wrong number format");
        }

    }

    public static void checkIsValidEmail(String...args) throws ValidatorException{
        try {
            checkStringsByPattern(EMAIL_PATTERN, args);
        } catch (ValidatorException e){
            throw new ValidatorException(e.getMessage()+" wrong email format");
        }

    }


    private static void checkStringsByPattern(String patternString, String...args) throws ValidatorException{
        int length = args.length;
        int index=0;
        Pattern pattern = Pattern.compile(patternString);
        while (index<length){
            Matcher matcher = pattern.matcher(args[index]);
            if(!matcher.matches()){
                throw new ValidatorException("Wrong input:");
            }
            index++;
        }
    }
}
