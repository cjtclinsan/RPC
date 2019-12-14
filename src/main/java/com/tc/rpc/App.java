package com.tc.rpc;

import com.tc.IHelloService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        //动态代理
        RpcProxyClient client = new RpcProxyClient();
        IHelloService helloService =
                client.clientProxy(IHelloService.class, "localhost", 8080);
        String result = helloService.sayHello("tcc");

        System.out.println(result);
    }
}
