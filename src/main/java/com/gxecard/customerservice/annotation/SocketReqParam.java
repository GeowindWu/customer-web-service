package com.gxecard.customerservice.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketReqParam {
    int order();
    int length();
}
