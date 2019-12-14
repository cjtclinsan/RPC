package com.tc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author taosh
 * @create 2019-12-14 15:11
 */
public class App {
    public static void main(String[] args) {
//        IHelloService service = new HelloServiceImpl();
//
//        RpcProxyServer server = new RpcProxyServer();
//        server.publisher(service, 8080);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext) applicationContext).start();
    }
}
