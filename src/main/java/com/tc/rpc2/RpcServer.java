package com.tc.rpc2;

import java.io.IOException;

/**
 * Created by Administrator on 2018/8/30.
 */
public interface RpcServer {
    public void stop();
    public void start() throws IOException;
    public void register(Class serviceInterface, Class impl);
    public boolean isRunning();
    public int getPort();
}
