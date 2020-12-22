package uk.ac.shu.centric.support;

import org.apache.commons.lang3.StringUtils;

public class Result<T> {

    private final T entity;
    private final boolean success;
    private final String message;

    private Result(T entity, boolean success, String message) {
        this.entity = entity;
        this.success = success;
        this.message = message;
    }

    public static <T> Result<T> successResult(T entity) {
        return new Result<>(entity, true, StringUtils.EMPTY);
    }

    public static <T> Result<T> failResult(String message) {
        return new Result<>(null, false, message);
    }

    public static <T> Result<T> failResult(Result<?> otherResult) {
        return new Result<>(null, false, otherResult.getMessage());
    }

    // region Properties

    public T getEntity() {
        return entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public String getMessage() {
        return message;
    }

    // endregion

}
