package com.webbdong.aop.agent.test.bean;

/**
 * Person
 * @author Webb Dong
 * @date 2021-02-23 8:10 PM
 */
public class Person {

    public int getAge(int age) {
        System.out.println("Target method Person.getAge(int)");
        return age * 2;
    }

}
