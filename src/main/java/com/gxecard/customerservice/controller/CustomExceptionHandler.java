package com.gxecard.customerservice.controller;

import com.google.common.collect.ImmutableMap;
import com.gxecard.customerservice.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MessageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> messageExceptionHandler(Exception ex) {
        MessageException exception = (MessageException) ex;
        return ImmutableMap.of("responseCode", exception.getErrorCode(),
                "errorDescription", exception.getErrorDescription());
    }

}
