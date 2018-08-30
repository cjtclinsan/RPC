package com.tc.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/8/29.
 */
public class RpcServerProxy {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(Object serivces, int port){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            while (true){
                Socket socket = serverSocket.accept();      //接收一个请求bio
                executorService.execute(new ProcessorHandler(socket, serivces));
            }
        }catch (Exception e){

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
