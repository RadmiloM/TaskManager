package com.radmilo.taskmanager.error;

public class Errors {

    String field;
    String message;

    public Errors() {

    }

    public Errors(String field, String message) {
        this.field = field;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClientErrorResponse{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
