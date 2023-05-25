package com.example.jscodestudy.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class BincologException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public BincologException(String message) {
        super(message);
    }

    public BincologException(String message, Throwable cause) {
        super(message, cause);
    }

    // 해당 예외가 어떤 코드인지
    public abstract int getStatusCode();


    // 특정 단어를 포함하지 못하도록
    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
