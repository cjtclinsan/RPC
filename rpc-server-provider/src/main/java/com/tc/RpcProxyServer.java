package com.tc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author taosh
 * @create 2019-12-14 14:43
 */
public class RpcProxyServer {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(Object service, int port){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            //不断接受请求
            while (true){
                Socket socket = serverSocket.accept();
                //每一个socket交给一个processorHandler处理
                executorService.execute(new ProcessorHandler(service, socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if( serverSocket != null ){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
