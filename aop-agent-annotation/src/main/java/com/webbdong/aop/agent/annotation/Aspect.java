package com.webbdong.aop.agent.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP 切面 注解
 * @author Webb Dong
 * @date 2021-02-14 15:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Aspect {

    /**
     * AOP 切面 bean id
     */
    String ref() default "";

}
