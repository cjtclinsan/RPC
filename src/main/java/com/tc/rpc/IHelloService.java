package com.tc.rpc;

/**
 * Created by Administrator on 2018/8/29.
 */
public interface IHelloService {
    String sayHello(String content);

    String saveUser(User user);
}
