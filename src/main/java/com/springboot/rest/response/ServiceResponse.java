package com.springboot.rest.response;

public class ServiceResponse<Type> {
    protected int code;
    protected Type data;
    protected String message;

    public ServiceResponse() {
    }

    public ServiceResponse(int code, Type data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(Type data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public Type getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
