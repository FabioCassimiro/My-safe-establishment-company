package br.com.mysafeestablishmentcompany.exception;

public class OrderPadNotFoundException extends Exception{
    public OrderPadNotFoundException() {
        super();
    }

    public OrderPadNotFoundException(String message) {
        super(message);
    }

    public OrderPadNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
