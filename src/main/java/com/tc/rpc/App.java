package com.tc.rpc;

import com.tc.IHelloService;
import com.tc.IPaymentService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        //动态代理
        RpcProxyClient client = new RpcProxyClient();
//        IHelloService helloService =
//                client.clientProxy(IHelloService.class, "localhost", 8080);

        IPaymentService paymentService = client.clientProxy(IPaymentService.class, "localhost", 8080);
        paymentService.doPay();
    }
}
