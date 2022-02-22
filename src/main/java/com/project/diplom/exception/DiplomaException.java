package com.project.diplom.exception;

public class DiplomaException extends RuntimeException {

    private ExceptionCode type;

    public DiplomaException(String message, ExceptionCode type) {
        super(message);
        this.type = type;
    }

    public ExceptionCode getStatus() {
        return type;
    }
}
