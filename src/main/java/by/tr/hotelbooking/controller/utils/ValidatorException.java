package by.tr.hotelbooking.controller.utils;

import by.tr.hotelbooking.exception.HotelbookingException;

public class ValidatorException extends HotelbookingException {
    public ValidatorException() {
        super();
    }

    public ValidatorException(String msg) {
        super(msg);
    }

    public ValidatorException(String msg, Exception e) {
        super(msg, e);
    }
    public ValidatorException(Exception e) {
        super(e);
    }
}
