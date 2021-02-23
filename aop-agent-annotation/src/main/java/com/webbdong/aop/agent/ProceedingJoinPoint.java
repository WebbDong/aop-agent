package com.webbdong.aop.agent;

/**
 * ProceedingJoinPoint
 * @author Webb Dong
 * @date 2021-02-14 21:10
 */
public interface ProceedingJoinPoint {

    /**
     * 执行目标方法
     * @return 返回目标方法的返回值
     * @throws Throwable 抛出异常
     */
    Object proceed() throws Throwable;

    /**
     * 执行目标方法并重新传递新指定的参数
     * @param args 参数
     * @return 返回目标方法的返回值
     * @throws Throwable 抛出异常
     */
    Object proceed(Object... args) throws Throwable;

    /**
     * 获取被代理的目标对象
     * @return 返回目标对象
     */
    Object getTarget();

    /**
     * 获取参数
     * @return 返回参数数组
     */
    Object[] getArgs();

    /**
     * 获取代理对象
     * @return 返回代理对象
     */
    Object getThis();

}
