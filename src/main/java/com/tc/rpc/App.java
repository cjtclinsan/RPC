package com.tc.rpc;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        IHelloService service = new HelloServiceImpl();

        RpcServerProxy rpcServerProxy = new RpcServerProxy();

        rpcServerProxy.publisher(service,8080);

    }
}
