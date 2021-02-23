package com.webbdong.aop.agent.model;

import com.webbdong.aop.agent.aop.AopAdviceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AOP 通知
 * @author Webb Dong
 * @date 2021-02-16 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AopAdvice implements Comparable<AopAdvice> {

    /**
     * 通知类型
     */
    private AopAdviceTypeEnum adviceType;

    /**
     * 通知方法名
     */
    private String methodName;

    @Override
    public int compareTo(AopAdvice o) {
        return this.methodName.compareTo(o.methodName);
    }

}
