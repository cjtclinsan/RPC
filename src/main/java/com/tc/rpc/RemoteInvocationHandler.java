package com.tc.rpc;

import com.tc.RpcRequest;
import com.tc.rpc.discovery.IServiceDiscovery;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author taosh
 * @create 2019-12-14 16:01
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private IServiceDiscovery serviceDiscovery;
    private String version;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求数据的包装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);

        String serviceName = rpcRequest.getClassName();
        if(!StringUtils.isEmpty(version)){
            serviceName = serviceName + "-" + version;
        }

        String serviceAddress = serviceDiscovery.discovery(serviceName);

        //远程通信
        RpcNetTransport transport = new RpcNetTransport(serviceAddress);
        Object result = transport.send(rpcRequest);

        return result;
    }
}
