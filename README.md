# aop agent
使用 byte-buddy agent 实现类似 spring aop 注解方式的 aop agent

### 1. 项目结构介绍
- aop-agent-annotation: 提供给使用者的注解
- aop-agent-core: aop agent 核心功能
- aop-agent-test: 测试例子

### 2. 使用方式
#### 2.1 添加注解依赖
```xml
<dependency>
    <groupId>com.webbdong</groupId>
    <artifactId>aop-agent-annotation</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

#### 2.2 定义目标类
```java
public class Dog {

    public void run() {
        System.out.println("Target method Dog.run()");
    }

}
```

#### 2.3 定义切面
```java
@Aspect
public class DogAspect {

    @Pointcut(id = "dogAspectPointcut", targetClassName = "com.webbdong.aop.agent.test.bean.Dog")
    public void pointcut() {

    }

    /**
     * 前置通知
     */
    @Before
    public void beforeAdvice() {
        System.out.println("DogAspect.beforeAdvice1()");
    }

    /**
     * 后置通知
     */
    @After
    public void afterAdvice() {
        System.out.println("DogAspect.afterAdvice1()");
    }

    /**
     * 后置返回通知
     */
    @AfterReturning
    public void afterReturning() {
        System.out.println("DogAspect.afterReturning1()");
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing
    public void afterThrowing() {
        System.out.println("DogAspect.afterThrowing1()");
    }

    /**
     * 环绕通知
     */
    @Around
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("DogAspect.around() start");
        System.out.println("DogAspect.around() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("DogAspect.around() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("DogAspect.around() proxy class name: " + joinPoint.getThis().getClass().getName());
        Object ret = joinPoint.proceed();
        System.out.println("DogAspect.around() end");
        return ret;
    }

}
```

#### 2.4 在 jvm agent 启动参数
```
-javaagent:D:/develop/workspace/java/aop-agent/aop-agent-core/target/aop-agent-core-1.0-SNAPSHOT.jar
```

替换自己的路径运行即可