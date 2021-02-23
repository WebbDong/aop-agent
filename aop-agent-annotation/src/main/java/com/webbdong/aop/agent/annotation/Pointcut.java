package com.webbdong.aop.agent.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切点
 * @author Webb Dong
 * @date 2021-02-14 16:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Pointcut {

    /**
     * id
     */
    String id();

    /**
     * 需要被代理的目标对象的 class name
     */
    String targetClassName();

}
