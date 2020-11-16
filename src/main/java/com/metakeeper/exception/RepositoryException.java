package com.metakeeper.exception;

import com.arangodb.ArangoDBException;
import org.jetbrains.annotations.Nullable;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 04.11.2020
 */
public class RepositoryException extends RuntimeException {
    private final Integer code;
    private final Integer error;
    private final String details;

    public RepositoryException(String message) {
        super(message);
        this.code = null;
        this.error = null;
        this.details = message;
    }

    public RepositoryException(String message, Integer code, Integer error, String details) {
        super(message);
        this.code = code;
        this.error = error;
        this.details = details;
    }

    public RepositoryException(ArangoDBException e) {
        super(e.getMessage());
        this.code = e.getResponseCode();
        this.error = e.getErrorNum();
        this.details = e.getErrorMessage();
    }

    public @Nullable Integer getCode() {
        return code;
    }

    public @Nullable Integer getError() {
        return error;
    }

    public @Nullable String getDetails() {
        return details;
    }
}
