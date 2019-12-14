package com.tc.rpc;

import java.lang.reflect.Proxy;

/**
 * @author taosh
 * @create 2019-12-14 15:59
 */
public class RpcProxyClient {

    public <T> T clientProxy(final Class<T> interfaceClass, final String host, final int port){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new RemoteInvocationHandler(host, port));
    }
}
