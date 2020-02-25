package com.tc.rpc;

import com.tc.IHelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws InterruptedException {
        //动态代理
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);

        IHelloService helloService = rpcProxyClient.clientProxy(IHelloService.class, "V1.0.0");

        for( int i = 0; i < 100; i++ ){
            Thread.sleep(1000);
            System.out.println(helloService.sayHello("tccc"));
        }
    }
}
