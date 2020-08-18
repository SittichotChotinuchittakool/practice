package com.example.wongnai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 7804192590154128420L;

    public ResourceNotFoundException(String message){
//        super(message + " not found");
    }

    public ResourceNotFoundException(Class clzz){
        super(clzz.getName() + " not found");
    }

    public ResourceNotFoundException(){
        super("Review Not Found");
    }

    public String getCustomMessage(){
        return "Review Not Found";
    }
}
