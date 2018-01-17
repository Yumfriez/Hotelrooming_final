package by.tr.hotelbooking.exception;

public class HotelbookingException extends Exception {
    public HotelbookingException() {
        super();
    }

    public HotelbookingException(String message) {
        super(message);
    }

    public HotelbookingException(Exception exception) {
        super(exception);
    }

    public HotelbookingException(String message, Exception exception) {
        super(message, exception);
    }
}
