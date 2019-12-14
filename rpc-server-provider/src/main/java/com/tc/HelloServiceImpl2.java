package com.tc;

/**
 * @author taosh
 * @create 2019-12-14 14:40
 */
@RpcService(value = IHelloService.class, version = "V2.0.0")
public class HelloServiceImpl2 implements IHelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("【V2.0.0】request in: "+name);
        return "sya Hello:"+name;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("【V2.0.0】request in: "+user);
        return "save User:"+user;
    }
}
