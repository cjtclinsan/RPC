package com.tc.rpc.discovery;

/**
 * @author taosh
 * @create 2020-02-25 14:46
 */
public interface IServiceDiscovery {
    /**
     * 根据服务名称返回服务地址
     */
    String discovery(String serviceName);
}
