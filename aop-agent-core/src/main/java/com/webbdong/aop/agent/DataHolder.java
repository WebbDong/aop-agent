package com.webbdong.aop.agent;

import com.webbdong.aop.agent.aop.AopAdviceTypeEnum;
import com.webbdong.aop.agent.model.AopAdvice;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据缓存
 * @author Webb Dong
 * @date 2021-02-23 12:14 AM
 */
@Getter
public enum DataHolder {

    INSTANCE;

    /**
     * 切面实例 map
     * key: 目标对象的 class name, value: 切面实例
     */
    private final Map<String, Object> aspectBeanCaching = new HashMap<>();

    /**
     * 切面通知方法 map
     * key: 目标对象的 class name, value: Map<AopAdviceTypeEnum, List<AopAdvice>> 实例
     */
    private final Map<String, Map<AopAdviceTypeEnum, List<AopAdvice>>> aspectAdviceMap = new HashMap<>();

}
