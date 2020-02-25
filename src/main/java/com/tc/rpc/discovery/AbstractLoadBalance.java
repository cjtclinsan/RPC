package com.tc.rpc.discovery;

import java.util.List;

/**
 * @author taosh
 * @create 2020-02-25 14:48
 */
public abstract class AbstractLoadBalance implements LoadBalanceStrategy {
    
    @Override
    public String selectHost(List<String> repos){
        //repos可能为空，可能只有一个
        if( repos == null || repos.size() == 0 ){
            return null;
        }else if( repos.size() == 1 ) {
            return repos.get(0);
        }else {
            return doSelect(repos);
        }
    }

    //负载均衡算法
    protected abstract String doSelect(List<String> repos);
}
