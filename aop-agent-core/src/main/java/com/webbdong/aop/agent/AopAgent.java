package com.webbdong.aop.agent;

import com.webbdong.aop.agent.proxy.AopProxyInterceptor;
import com.webbdong.aop.agent.proxy.Morphing;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * AOP Agent
 * @author Webb Dong
 * @date 2021-02-22 5:08 PM
 */
public class AopAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        PackageScanner packageScanner = new PackageScanner();
        packageScanner.scanPackage();

        String[] interceptorClassNameArr = new String[packageScanner.getInterceptorClassNameList().size()];
        new AgentBuilder.Default()
                .type(ElementMatchers.namedOneOf(packageScanner.getInterceptorClassNameList().toArray(interceptorClassNameArr)))
                .transform((builder, type, classLoader, module) -> builder.method(ElementMatchers.any())
                .intercept(MethodDelegation.withDefaultConfiguration()
                                .withBinders(Morph.Binder.install(Morphing.class))
                                .to(new AopProxyInterceptor())))
                .with(new AgentListener())
                .installOn(inst);
    }

    private static class AgentListener extends AgentBuilder.Listener.Adapter {

        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//            System.out.println("typeName = " + typeName);
        }

    }

}
