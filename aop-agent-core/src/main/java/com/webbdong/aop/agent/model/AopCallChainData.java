package com.webbdong.aop.agent.model;

import com.webbdong.aop.agent.aop.AopAdviceTypeEnum;
import com.webbdong.aop.agent.proxy.Morphing;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 方法调用链数据
 * @author Webb Dong
 * @date 2021-02-23 2:14 AM
 */
@Data
@Builder
public class AopCallChainData {

    /**
     * 通知方法 map
     */
    private Map<AopAdviceTypeEnum, List<AopAdvice>> adviceDefMap;

    /**
     * 环绕通知方法定义集合
     */
    private List<AopAdvice> aroundDefAdviceList;

    /**
     * 代理实例
     */
    private Object proxy;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 切面实例
     */
    private Object aspect;

    /**
     * 目标方法
     */
    private Morphing targetMethod;

    /**
     * 目标方法参数
     */
    private Object[] args;

}
