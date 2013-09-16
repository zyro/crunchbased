package com.github.zyro.crunchbased.service;

/** Exception to indicate a problem has occurred while retrieving data. */
public class ClientException extends Exception {

    /** Initialise with no underlying cause. */
    public ClientException() {
        super();
    }

    /**
     * Construct with a given cause.
     *
     * @param cause The original Throwable cause of this exception.
     */
    public ClientException(final Throwable cause) {
        super(cause);
    }

}