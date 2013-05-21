package com.github.zyro.crunchbase.service;

import lombok.NoArgsConstructor;

/** Exception to indicate a problem has occurred while retrieving data. */
@NoArgsConstructor
public class ClientException extends Exception {

    /**
     * Construct with a given cause.
     *
     * @param cause The original Throwable cause of this exception.
     */
    public ClientException(final Throwable cause) {
        super(cause);
    }

}