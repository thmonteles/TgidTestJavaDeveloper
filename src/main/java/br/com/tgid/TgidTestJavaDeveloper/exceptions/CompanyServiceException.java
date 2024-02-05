package br.com.tgid.TgidTestJavaDeveloper.exceptions;

public class CompanyServiceException extends Exception {

    public CompanyServiceException() {
        super();
    }

    public CompanyServiceException(String message) {
        super(message);
    }

    public CompanyServiceException(String message, Throwable t) {
        super(message, t);
    }
}
