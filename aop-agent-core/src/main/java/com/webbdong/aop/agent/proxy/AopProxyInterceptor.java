package com.webbdong.aop.agent.proxy;

import com.webbdong.aop.agent.DataHolder;
import com.webbdong.aop.agent.aop.AopAdviceTypeEnum;
import com.webbdong.aop.agent.aop.ProceedingJoinPointImpl;
import com.webbdong.aop.agent.aop.methodinvocation.AroundAdviceMethodInvocation;
import com.webbdong.aop.agent.aop.methodinvocation.MethodInvocation;
import com.webbdong.aop.agent.aop.methodinvocation.NormalAdviceMethodInvocation;
import com.webbdong.aop.agent.aop.methodinvocation.TargetMethodInvocation;
import com.webbdong.aop.agent.model.AopAdvice;
import com.webbdong.aop.agent.model.AopCallChainData;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Aop 代理拦截器
 * @author Webb Dong
 * @date 2021-02-22 7:09 PM
 */
public class AopProxyInterceptor {

    /**
     * 方法拦截
     * @param targetObj 目标对象
     * @param proxy 代理对象
     * @param targetClass 目标类
     * @param morphing 目标方法执行，可传递方法参数
     * @param allArgs 方法所有参数
     * @return 返回目标方法的返回值
     * @throws Throwable 抛出所有异常
     */
    @RuntimeType
    public Object intercept(@This Object targetObj,
                            @Super Object proxy,
                            @Origin Class<?> targetClass,
                            @Morph Morphing morphing,
                            @AllArguments Object[] allArgs) throws Throwable {
        Map<AopAdviceTypeEnum, List<AopAdvice>> adviceDefMap = DataHolder.INSTANCE.getAspectAdviceMap()
                .get(targetClass.getName());
        if (adviceDefMap == null || adviceDefMap.size() == 0) {
            return morphing.invoke(allArgs);
        }

        List<AopAdvice> aroundDefAdviceList = adviceDefMap.get(AopAdviceTypeEnum.AROUND);
        Object ret;
        Object aspect = DataHolder.INSTANCE.getAspectBeanCaching().get(targetClass.getName());
        if (adviceDefMap == null || aroundDefAdviceList == null || aroundDefAdviceList.size() == 0) {
            ret = createTargetMethodInvocation(adviceDefMap, morphing, aspect).invoke(allArgs);
        } else {
            MethodInvocation aroundMethodInvocation = createAroundAdviceMethodInvocationCallChain(
                    AopCallChainData.builder()
                            .adviceDefMap(adviceDefMap)
                            .aroundDefAdviceList(aroundDefAdviceList)
                            .proxy(proxy)
                            .target(targetObj)
                            .aspect(aspect)
                            .targetMethod(morphing)
                            .args(allArgs)
                            .build());
            ret = aroundMethodInvocation.invoke(allArgs);
        }
        return ret;
    }

    /**
     * 创建环绕通知方法链
     * @param data 方法调用链数据
     * @return 返回第一个环绕通知方法调用器
     */
    private MethodInvocation createAroundAdviceMethodInvocationCallChain(AopCallChainData data) {
        AroundAdviceMethodInvocation firstAroundAdvice = null;
        AroundAdviceMethodInvocation prevAroundAdvice = null;
        for (AopAdvice aopAdviceDefinition : data.getAroundDefAdviceList()) {
            AroundAdviceMethodInvocation newAroundAdvice = AroundAdviceMethodInvocation.builder()
                    .aspect(data.getAspect())
                    .invokeMethodName(aopAdviceDefinition.getMethodName())
                    .build();
            if (firstAroundAdvice == null) {
                firstAroundAdvice = newAroundAdvice;
            }
            if (prevAroundAdvice != null) {
                prevAroundAdvice.setNextJoinPoint(ProceedingJoinPointImpl.builder()
                        .args(data.getArgs())
                        .proxy(data.getProxy())
                        .target(data.getTarget())
                        .methodInvocation(newAroundAdvice)
                        .build());
            }
            prevAroundAdvice = newAroundAdvice;
        }
        prevAroundAdvice.setNextJoinPoint(ProceedingJoinPointImpl.builder()
                .args(data.getArgs())
                .proxy(data.getProxy())
                .target(data.getTarget())
                .methodInvocation(createTargetMethodInvocation(data.getAdviceDefMap(),
                        data.getTargetMethod(), data.getAspect()))
                .build());
        return firstAroundAdvice;
    }

    /**
     * 创建目标方法调用器
     * @param adviceDefMap 通知方法 map
     * @param targetMethod 目标方法
     * @param aspect 切面实例
     * @return 返回目标方法调用器
     */
    private MethodInvocation createTargetMethodInvocation(Map<AopAdviceTypeEnum, List<AopAdvice>> adviceDefMap,
                                                          Morphing targetMethod, Object aspect) {
        return TargetMethodInvocation.builder()
                .beforeAdviceMethodList(createNormalAdviceMethodList(adviceDefMap, AopAdviceTypeEnum.BEFORE, aspect))
                .afterReturningMethodList(createNormalAdviceMethodList(adviceDefMap, AopAdviceTypeEnum.AFTER_RETURNING, aspect))
                .afterThrowingMethodList(createNormalAdviceMethodList(adviceDefMap, AopAdviceTypeEnum.AFTER_THROWING, aspect))
                .afterAdviceMethodList(createNormalAdviceMethodList(adviceDefMap, AopAdviceTypeEnum.AFTER, aspect))
                .targetMethod(targetMethod)
                .aspect(aspect)
                .build();
    }

    /**
     * 创建普通通知方法（包含所有前置通知和所有后置通知）调用器集合
     * @param adviceDefMap 通知方法 map
     * @param adviceType 通知类型
     * @param aspect 切面实例
     * @return 返回普通通知方法的调用器集合
     */
    private List<MethodInvocation> createNormalAdviceMethodList(Map<AopAdviceTypeEnum, List<AopAdvice>> adviceDefMap,
                                                                AopAdviceTypeEnum adviceType, Object aspect) {
        List<AopAdvice> adviceList = adviceDefMap.get(adviceType);
        if (adviceList == null || adviceList.size() == 0) {
            return null;
        }
        return adviceList.stream()
                .map(def -> NormalAdviceMethodInvocation.builder()
                        .aspect(aspect)
                        .invokeMethodName(def.getMethodName())
                        .build())
                .collect(Collectors.toList());
    }

}
