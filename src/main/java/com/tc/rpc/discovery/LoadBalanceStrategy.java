package com.tc.rpc.discovery;

import java.util.List;

/**
 * @author taosh
 * @create 2020-02-25 14:48
 */
public interface LoadBalanceStrategy {
    String selectHost(List<String> repos);
}
