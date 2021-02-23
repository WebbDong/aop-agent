package com.webbdong.aop.agent.aop.methodinvocation;

/**
 * 方法调用器接口
 * @author Webb Dong
 * @date 2021-02-18 22:22
 */
public interface MethodInvocation {

    /**
     * 执行调用方法
     * @param args 方法参数
     * @return 返回值
     */
    Object invoke(Object... args) throws Throwable;

}
