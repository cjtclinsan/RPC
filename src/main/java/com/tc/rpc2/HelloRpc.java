package com.tc.rpc2;

/**
 * Created by Administrator on 2018/8/30.
 */
public class HelloRpc implements IHelloRpc {
    @Override
    public String sayHi(String name) {
        System.out.println("Hi,"+name);
        return "Hi,"+name;
    }
}
