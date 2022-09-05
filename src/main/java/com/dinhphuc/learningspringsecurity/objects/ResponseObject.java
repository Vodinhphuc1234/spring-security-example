package com.dinhphuc.learningspringsecurity.objects;

public class ResponseObject {
    private int error;
    private String message;
    private Object object;

    public ResponseObject(int error, String message, Object object) {
        this.error = error;
        this.message = message;
        this.object = object;
    }

    public ResponseObject() {
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
