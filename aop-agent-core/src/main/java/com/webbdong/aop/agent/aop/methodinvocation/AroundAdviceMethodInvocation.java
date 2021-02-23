package com.webbdong.aop.agent.aop.methodinvocation;

import com.webbdong.aop.agent.ProceedingJoinPoint;
import com.webbdong.aop.agent.aop.ProceedingJoinPointImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Method;

/**
 * 环绕通知方法调用器
 * @author Webb Dong
 * @date 2021-02-18 22:33
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AroundAdviceMethodInvocation extends AbstractAdviceMethodInvocation {

    /**
     * 下一个 join point
     */
    private ProceedingJoinPoint nextJoinPoint;

    @Override
    public Object invoke(Object... args) throws Throwable {
        Method aroundMethod = aspect.getClass().getMethod(invokeMethodName, ProceedingJoinPoint.class);
        ((ProceedingJoinPointImpl) nextJoinPoint).setArgs(args);
        return aroundMethod.invoke(aspect, nextJoinPoint);
    }

}
