package com.webbdong.aop.agent.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AOP 通知类型枚举
 * @author Webb Dong
 * @date 2021-02-16 22:21
 */
@Getter
@AllArgsConstructor
public enum AopAdviceTypeEnum {

    /**
     * 前置通知
     */
    BEFORE(0, "before"),

    /**
     * 后置通知
     */
    AFTER(1, "after"),

    /**
     * 后置返回通知
     */
    AFTER_RETURNING(2, "after-returning"),

    /**
     * 后置异常通知
     */
    AFTER_THROWING(3, "after-throwing"),

    /**
     * 环绕通知
     */
    AROUND(4, "around");

    private final int type;

    private final String name;

    /**
     * 是否是后置类型的通知
     * @param typeEnum AOP 通知类型枚举
     * @return 是后置类型的通知返回 true 否则返回 false
     */
    public static boolean isAfterTypeAdvice(AopAdviceTypeEnum typeEnum) {
        return typeEnum == AFTER
                || typeEnum == AFTER_RETURNING
                || typeEnum == AFTER_THROWING;
    }

}
