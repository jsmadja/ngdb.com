package com.ngdb.persistence;

public class ElementNonTrouveException extends Exception {

    public ElementNonTrouveException(String message, Throwable t) {
        super(message, t);
    }

    public ElementNonTrouveException(String message) {
        super(message);
    }

    public ElementNonTrouveException(Throwable t) {
        super(t);
    }
}
