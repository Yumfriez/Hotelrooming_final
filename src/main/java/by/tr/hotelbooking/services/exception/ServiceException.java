package by.tr.hotelbooking.services.exception;

import by.tr.hotelbooking.exception.HotelbookingException;

public class ServiceException extends HotelbookingException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception exception) {
        super(exception);
    }

    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }
}
