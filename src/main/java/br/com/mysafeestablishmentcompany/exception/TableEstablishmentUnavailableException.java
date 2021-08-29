package br.com.mysafeestablishmentcompany.exception;

public class TableEstablishmentUnavailableException extends Exception {

    public TableEstablishmentUnavailableException() {
        super();
    }

    public TableEstablishmentUnavailableException(String message) {
        super(message);
    }

    public TableEstablishmentUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
