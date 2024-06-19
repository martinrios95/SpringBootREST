package com.springboot.rest.response;

import java.util.List;

public class ListServiceResponse<Type> extends ServiceResponse<List<Type>> {
    protected ListServiceResponse(){
        super();
    }
    public ListServiceResponse(int code, String message){
        super(code, message);
    }
    public ListServiceResponse(int code, List<Type> data, String message){
        super(code, data, message);
    }
}
