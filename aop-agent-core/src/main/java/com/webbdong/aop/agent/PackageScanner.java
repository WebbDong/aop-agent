package com.webbdong.aop.agent;

import com.webbdong.aop.agent.annotation.After;
import com.webbdong.aop.agent.annotation.AfterReturning;
import com.webbdong.aop.agent.annotation.AfterThrowing;
import com.webbdong.aop.agent.annotation.Around;
import com.webbdong.aop.agent.annotation.Aspect;
import com.webbdong.aop.agent.annotation.Before;
import com.webbdong.aop.agent.annotation.Pointcut;
import com.webbdong.aop.agent.aop.AopAdviceTypeEnum;
import com.webbdong.aop.agent.model.AopAdvice;
import com.webbdong.aop.agent.util.ListUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 包扫描器
 * @author Webb Dong
 * @date 2021-02-22 6:40 PM
 */
@Getter
public class PackageScanner {

    private final List<String> interceptorClassNameList = new ArrayList<>();

    public PackageScanner() {
    }

    /**
     * 扫描包
     */
    public void scanPackage() {
        Reflections reflections = new Reflections(AopAgent.class.getPackage().getName());
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Aspect.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            handleClassAspectAnnotation(aClass, aClass.getAnnotation(Aspect.class));
        }
    }

    /**
     * 处理类上的 Aspect 注解
     * @param aClass 当前类的字节码
     * @param aspect 当前类上的 Aspect 注解实例
     */
    @SneakyThrows
    private void handleClassAspectAnnotation(Class<?> aClass, Aspect aspect) {
        Method[] declaredMethods = aClass.getDeclaredMethods();
        if (declaredMethods.length == 0) {
            return;
        }

        Map<AopAdviceTypeEnum, List<AopAdvice>> adviceDefMap = new HashMap<>();
        String targetName = null;
        for (Method m : declaredMethods) {
            if (m.isAnnotationPresent(Pointcut.class)) {
                Pointcut pointcut = m.getAnnotation(Pointcut.class);
                targetName = pointcut.targetClassName();
                interceptorClassNameList.add(targetName);
            } else if (isAdviceAnnotation(m)) {
                initAdviceDefMap(adviceDefMap, m.getName(), getAopAdviceTypeEnumByAdviceAnnotation(m));
            }
        }

        DataHolder.INSTANCE.getAspectBeanCaching().put(targetName, aClass.newInstance());
        DataHolder.INSTANCE.getAspectAdviceMap().put(targetName, adviceDefMap);
    }

    /**
     * 初始化 AdviceDefMap
     * @param adviceDefMap Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> 实例
     * @param methodName 通知方法名
     * @param adviceType 通知类型
     */
    protected void initAdviceDefMap(Map<AopAdviceTypeEnum, List<AopAdvice>> adviceDefMap,
                                    String methodName,
                                    AopAdviceTypeEnum adviceType) {
        AopAdvice adviceDef = AopAdvice.builder()
                .methodName(methodName)
                .adviceType(adviceType)
                .build();
        List<AopAdvice> adviceDefList = adviceDefMap.computeIfAbsent(
                adviceDef.getAdviceType(), k -> new ArrayList<>(2));
        ListUtils.orderedAdd(adviceDefList, adviceDef,
                AopAdviceTypeEnum.isAfterTypeAdvice(adviceDef.getAdviceType()));
    }

    /**
     * 根据方法上的通知注解获取对应的通知类型枚举
     * @param m 方法
     * @return 返回对应的通知类型枚举
     */
    private AopAdviceTypeEnum getAopAdviceTypeEnumByAdviceAnnotation(Method m) {
        if (m.isAnnotationPresent(Around.class)) {
            return AopAdviceTypeEnum.AROUND;
        } else if (m.isAnnotationPresent(Before.class)) {
            return AopAdviceTypeEnum.BEFORE;
        } else if (m.isAnnotationPresent(After.class)) {
            return AopAdviceTypeEnum.AFTER;
        } else if (m.isAnnotationPresent(AfterReturning.class)) {
            return AopAdviceTypeEnum.AFTER_RETURNING;
        } else {
            return AopAdviceTypeEnum.AFTER_THROWING;
        }
    }

    /**
     * 判断此方法是否有通知注解
     * @param m 方法
     * @return 存在通知注解返回 true，否则返回 false
     */
    private boolean isAdviceAnnotation(Method m) {
        return m.isAnnotationPresent(Around.class)
                || m.isAnnotationPresent(Before.class)
                || m.isAnnotationPresent(After.class)
                || m.isAnnotationPresent(AfterReturning.class)
                || m.isAnnotationPresent(AfterThrowing.class);
    }

}
