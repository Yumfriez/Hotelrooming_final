package by.tr.hotelbooking.dao.exception;

import by.tr.hotelbooking.exception.HotelbookingException;

public class DAOException extends HotelbookingException {

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception exception) {
        super(exception);
    }

    public DAOException(String message, Exception exception) {
        super(message, exception);
    }
}
