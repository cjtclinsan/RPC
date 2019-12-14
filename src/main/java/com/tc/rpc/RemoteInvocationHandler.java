package com.tc.rpc;

import com.tc.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author taosh
 * @create 2019-12-14 16:01
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求数据的包装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion("V2.0.0");

        //远程通信
        RpcNetTransport transport = new RpcNetTransport(host, port);
        Object result = transport.send(rpcRequest);

        return result;
    }
}
