package com.webbdong.aop.agent.proxy;

/**
 * 用于 byte-buddy 修改目标方法的参数
 * @author Webb Dong
 * @date 2021-02-23 1:09 AM
 */
public interface Morphing {

    /**
     * 执行目标方法
     * @param args 参数
     * @return 返回目标方法的返回值
     */
    Object invoke(Object... args);

}
