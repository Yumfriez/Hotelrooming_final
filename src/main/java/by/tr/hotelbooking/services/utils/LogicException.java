package by.tr.hotelbooking.services.utils;

import by.tr.hotelbooking.exception.HotelbookingException;

public class LogicException extends HotelbookingException {
    public LogicException() {
        super();
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(Exception exception) {
        super(exception);
    }

    public LogicException(String message, Exception exception) {
        super(message, exception);
    }
}
