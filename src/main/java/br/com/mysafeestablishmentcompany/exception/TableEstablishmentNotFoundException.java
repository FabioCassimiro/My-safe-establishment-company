package br.com.mysafeestablishmentcompany.exception;

public class TableEstablishmentNotFoundException extends Exception{
    public TableEstablishmentNotFoundException() {
    }

    public TableEstablishmentNotFoundException(String message) {
        super(message);
    }

    public TableEstablishmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
