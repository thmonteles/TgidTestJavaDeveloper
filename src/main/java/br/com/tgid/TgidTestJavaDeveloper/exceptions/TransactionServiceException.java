package br.com.tgid.TgidTestJavaDeveloper.exceptions;

public class TransactionServiceException  extends Exception {
    public TransactionServiceException() {
        super();
    }

    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(String message, Throwable t) {
        super(message, t);
    }
}
