package com.tc.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by Administrator on 2018/8/29.
 */
public class ProcessorHandler implements Runnable{

    Socket socket;
    Object service;

    public ProcessorHandler(){

    }

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        //处理逻辑
        System.out.println("开始处理客户端信息");

        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            //java反序列化
            RPCRequest rpcRequest = (RPCRequest) inputStream.readObject();
            Object result = invoke(rpcRequest);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object invoke(RPCRequest rpcRequest){
        Object[] params = rpcRequest.getParameter();
        Class<?>[] types = new Class[params.length];
        for ( int i = 0; i < params.length; i++ ){
            types[i] = params[i].getClass();
        }

        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), types);
            Object result = method.invoke(service,params);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
