package com.tc.rpc;

import com.tc.rpc.discovery.IServiceDiscovery;
import com.tc.rpc.discovery.ServiceDiscoveryWithZk;

import java.lang.reflect.Proxy;

/**
 * @author taosh
 * @create 2019-12-14 15:59
 */
public class RpcProxyClient {
    private IServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithZk();

    public <T> T clientProxy(final Class<T> interfaceClass, String version){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new RemoteInvocationHandler(serviceDiscovery, version));
    }
}
