package com.webbdong.aop.agent.aop.methodinvocation;

import lombok.experimental.SuperBuilder;

/**
 * 抽象通知方法调用器
 * @author Webb Dong
 * @date 2021-02-19 15:24
 */
@SuperBuilder
public abstract class AbstractAdviceMethodInvocation implements MethodInvocation {

    /**
     * 切面对象
     */
    protected Object aspect;

    /**
     * 调用的方法名
     */
    protected String invokeMethodName;

}
