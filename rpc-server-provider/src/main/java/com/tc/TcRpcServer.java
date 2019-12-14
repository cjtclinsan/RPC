package com.tc;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author taosh
 * @create 2019-12-14 16:45
 */
@Component
public class TcRpcServer implements ApplicationContextAware, InitializingBean {

    ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<String, Object> serviceMap = new HashMap<>();

    private int port;

    public TcRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket, serviceMap));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if( !serviceBeanMap.isEmpty()){
            for (Object serviceBean : serviceBeanMap.values()) {
                //得到注解

                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);

                //接口类
                String serviceName = rpcService.value().getName();
                //版本号
                String version = rpcService.version();
                serviceMap.put(serviceName+"-"+version, serviceBean);
            }
        }
    }
}
