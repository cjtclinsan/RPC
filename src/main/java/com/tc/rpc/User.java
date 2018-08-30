package com.tc.rpc;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/29.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 8220315918508912538L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
