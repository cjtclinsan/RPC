package com.tc;

/**
 * @author taosh
 * @create 2019-12-14 14:40
 */
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("request in: "+name);
        return "sya Hello:"+name;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in: "+user);
        return "save User:"+user;
    }
}
