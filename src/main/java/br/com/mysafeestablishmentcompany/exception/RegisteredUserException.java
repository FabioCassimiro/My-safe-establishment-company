package br.com.mysafeestablishmentcompany.exception;

public class RegisteredUserException extends Exception {

    public RegisteredUserException() {
        super();
    }

    public RegisteredUserException(String message) {
        super(message);
    }

    public RegisteredUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
