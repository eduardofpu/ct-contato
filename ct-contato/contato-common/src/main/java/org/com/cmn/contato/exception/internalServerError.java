package org.com.cmn.contato.exception;

public class internalServerError extends Exception {

    private static final long serialVersionUID = 1L;

    public internalServerError() {
        super();
    }

    public internalServerError(String message) {
        super(message);
    }
}