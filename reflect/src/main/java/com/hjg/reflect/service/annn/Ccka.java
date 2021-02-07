package com.hjg.reflect.service.annn;

import java.lang.annotation.*;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/5
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Ccka {

    String name() default "33";

    int age() default 20;
}
