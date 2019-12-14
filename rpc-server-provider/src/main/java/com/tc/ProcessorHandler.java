package com.tc;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author taosh
 * @create 2019-12-14 14:45
 */
public class ProcessorHandler implements Runnable{
    private Socket socket;
    private Map<String, Object> serviceMap;

    public ProcessorHandler( Socket socket, Map serviceMap) {
        this.socket = socket;
        this.serviceMap = serviceMap;
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
        } catch (Exception e) {
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

    private Object invoke(RpcRequest rpcRequest) throws Exception {
        //反射去拿到客户端请求的参数
        String serviceName = rpcRequest.getClassName();
        String version = rpcRequest.getVersion();
        if( !StringUtils.isEmpty(version) ){
            serviceName += "-"+version;
        }

        Object service = serviceMap.get(serviceName);
        if( service == null ){
            throw new RuntimeException("service not fount:"+serviceName);
        }

        Object[] args = rpcRequest.getParameters();
        Method method = null;
        if(args != null){
            //获得每个参数的类型
            Class<?>[] types = new Class[args.length];

            for( int i = 0; i < args.length; i++ ){
                types[i] = args[i].getClass();
            }

            //根据请求的类去加载
            Class clazz = Class.forName(rpcRequest.getClassName());
            //拿到方法  syaHello,saveUser等
            method = clazz.getMethod(rpcRequest.getMethodName(), types);
        }else {
            //根据请求的类去加载
            Class clazz = Class.forName(rpcRequest.getClassName());
            //拿到方法  syaHello,saveUser等
            method = clazz.getMethod(rpcRequest.getMethodName());
        }

        ////HelloServiceImpl  进行反射
        Object result = method.invoke(service,args);
        return result;
    }
}
