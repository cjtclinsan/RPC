package com.tc.rpc;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Administrator
 * @date 2018/8/29
 */
public class RPCRequest implements Serializable{


    private static final long serialVersionUID = -5633882173521553524L;
    private String className;
    private String methodName;
    private Object[] parameter;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "PRCRequest{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameter=" + Arrays.toString(parameter) +
                '}';
    }
}
