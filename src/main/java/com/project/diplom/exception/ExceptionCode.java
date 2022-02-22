package com.project.diplom.exception;

public enum ExceptionCode {
    VALIDATION_ERROR(400),
    UNAUTHORIZED(401),
    NO_PERMISSION(403),
    NO_RESULT(404),
    ERROR(500);


    private int code;

    ExceptionCode(int statusCode) {
        this.code = statusCode;
    }

    public int getCode() {
        return code;
    }
}
