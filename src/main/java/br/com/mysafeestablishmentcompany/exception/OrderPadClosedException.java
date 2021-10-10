package br.com.mysafeestablishmentcompany.exception;

public class OrderPadClosedException extends Exception{

    public OrderPadClosedException() {
        super();
    }

    public OrderPadClosedException(String message) {
        super(message);
    }

    public OrderPadClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
