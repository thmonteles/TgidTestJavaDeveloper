package br.com.tgid.TgidTestJavaDeveloper.exceptions;

public class ClientServiceException extends Exception{

        public ClientServiceException() {
            super();
        }

        public ClientServiceException(String message) {
            super(message);
        }

        public ClientServiceException(String message, Throwable t) {
            super(message, t);
        }

}
