package com.tc;

/**
 * @author taosh
 * @create 2019-12-14 14:33
 */
public interface IHelloService {
    String sayHello(String name);

    /**保存用户*/
    String saveUser(User user);
}
