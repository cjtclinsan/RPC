package com.tc.rpc;

/**
 * Created by Administrator on 2018/8/29.
 */
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String content) {
        System.out.println("HELLO "+content);
        return "success:hello "+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("user-->"+user.toString());
        return "success:"+user.toString();
    }
}
