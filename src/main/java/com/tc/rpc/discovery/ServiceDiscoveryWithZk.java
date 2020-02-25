package com.tc.rpc.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taosh
 * @create 2020-02-25 14:53
 */
public class ServiceDiscoveryWithZk implements IServiceDiscovery {
    CuratorFramework curatorFramework = null;
    List<String> serviceRepos = new ArrayList<>();

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("registry")
                .build();
        curatorFramework.start();
    }

    /**
     * 服务的查找
     * 设置监听
     * @param serviceName
     * @return
     */
    @Override
    public String discovery(String serviceName) {
        //完成了服务地址的查找（服务地址被删除）
        String path = "/"+serviceName;
        if( serviceRepos.isEmpty() ){
            try {
                serviceRepos = curatorFramework.getChildren().forPath(path);

                registerWatch(path);
            }catch ( Exception e ){
                e.printStackTrace();
            }
        }
        //针对已有的地址做负载均衡
        LoadBalanceStrategy loadBalanceStrategy = new RandomLoadBalance();
        return loadBalanceStrategy.selectHost(serviceRepos);
    }

    private void registerWatch(final String path) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener cacheListener = (curatorFramework1, pathChildrenCacheListener) -> {
            System.out.println("客户端收到节点变更信息!");
            //更新本地的缓存地址
            serviceRepos = curatorFramework1.getChildren().forPath(path);
        };

        nodeCache.getListenable().addListener(cacheListener);
        nodeCache.start();
    }
}
