package com.tc.rpc.discovery;

import java.util.List;
import java.util.Random;

/**
 * @author taosh
 * @create 2020-02-25 14:52
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> repos) {
        int length = repos.size();
        Random random = new Random();

        //随机获取一个地址
        return repos.get(random.nextInt(length));
    }
}
