package com.tc.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taosh
 * @create 2020-02-25 15:43
 */
@Configuration
public class SpringConfig {
    @Bean(name="rpcProxyClient")
    public RpcProxyClient proxyClient(){
        return new RpcProxyClient();
    }
}
