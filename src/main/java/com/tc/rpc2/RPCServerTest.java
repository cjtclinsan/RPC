package com.tc.rpc2;

import com.tc.rpc.HelloServiceImpl;

import java.io.IOException;

/**
 * Created by Administrator on 2018/8/30.
 */
public class RPCServerTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RpcServer server = new RpcServerImpl(8080);
                server.register(HelloRpc.class, HelloServiceImpl.class);
                try {
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
