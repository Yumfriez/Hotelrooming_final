package by.tr.hotelbooking.dao.exception;

public class ConnectionPoolException extends DAOException {

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String msg) {
        super(msg);
    }

    public ConnectionPoolException(Exception e) {
        super(e);
    }

    public ConnectionPoolException(String msg, Exception e) {
        super(msg, e);
    }
}
