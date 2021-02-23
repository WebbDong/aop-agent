package com.webbdong.aop.agent.test;

import com.webbdong.aop.agent.test.bean.Dog;
import com.webbdong.aop.agent.test.bean.Person;

/**
 * @author Webb Dong
 * @date 2021-02-22 5:24 PM
 */
public class AopAgentTest {

    // -javaagent:D:/develop/workspace/java/aop-agent/aop-agent-core/target/aop-agent-core-1.0-SNAPSHOT.jar
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.run();
        System.out.println("-------------------------------------");
        Person p = new Person();
        int ret = p.getAge(20);
        System.out.println("ret = " + ret);
    }

}
