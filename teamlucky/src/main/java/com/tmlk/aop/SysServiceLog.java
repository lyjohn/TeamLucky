package com.tmlk.aop;

import java.lang.annotation.*;

/**
 * Created by laiguoqiang on 15/5/23.
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysServiceLog {

    String description() default "";


    int code() default 0;
}