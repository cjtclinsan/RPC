package com.tc;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)     //修饰类或者接口
@Retention(RetentionPolicy.RUNTIME)
@Component    //被spring进行扫描
public @interface RpcService {
    Class<?> value();     //拿到服务接口

    String version() default "";
}
