package com.tc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author taosh
 * @create 2019-12-14 14:45
 */
public class ProcessorHandler implements Runnable{
    private Socket socket;
    private Object service;

    public ProcessorHandler(Object service, Socket socket) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            //输入流中应该有什么信息:请求类，方法名，参数...
            RpcRequest rpcRequest = (RpcRequest) in.readObject();

            Object result = invoke(rpcRequest);

            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(result);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if( in != null ){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if( out != null ){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //反射去拿到客户端请求的参数
        Object[] args = rpcRequest.getParameters();
        //获得每个参数的类型
        Class<?>[] types = new Class[args.length];

        for( int i = 0; i < args.length; i++ ){
            types[i] = args[i].getClass();
        }

        //根据请求的类去加载
        Class clazz = Class.forName(rpcRequest.getClassName());
        //拿到方法  syaHello,saveUser等
        Method method = clazz.getMethod(rpcRequest.getMethodName(), types);
        ////HelloServiceImpl  进行反射
        Object result = method.invoke(service,args);
        return result;
    }
}
