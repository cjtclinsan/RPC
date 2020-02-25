package com.tc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author taosh
 * @create 2019-12-14 16:46
 */
@Configuration
@ComponentScan(basePackages = "com.tc")
public class SpringConfig {

    @Bean(name = "tcRpcServer")
    public TcRpcServer rpcServer(){
        return new TcRpcServer(8080);
    }
}
