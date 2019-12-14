package com.tc;

import java.io.Serializable;

/**
 * @author taosh
 * @create 2019-12-14 14:50
 */
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -8178010624820594251L;
    private String className;

    private String methodName;

    private Object[] parameters;

    private String version;

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

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
