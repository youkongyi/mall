package com.youkongyi.mall.nosql.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

/**
  * @description： Es连接池
  *     com.youkongyi.mall.nosql.elasticsearch.config.ESClientPool
  * @author： Aimer
  * @crateDate： 2022/7/13 17:45
  */
public class ESClientPool {

    // 对象池配置类，不写也可以，采用默认配置
    private static final GenericObjectPoolConfig<ElasticsearchClient> poolConfig = new GenericObjectPoolConfig<>();

    // 采用默认配置maxTotal是8，池中有8个client
    static {
        poolConfig.setMaxIdle(200);
        poolConfig.setMaxTotal(20);
        poolConfig.setMinEvictableIdleTime(Duration.ofMillis(1000L*3L));
    }

    // 要池化的对象的工厂类，这个是我们要实现的类
    private static final EsClientPoolFactory esClientPoolFactory = new EsClientPoolFactory();

    // 利用对象工厂类和配置类生成对象池
    private static final GenericObjectPool<ElasticsearchClient> clientPool = new GenericObjectPool<>(esClientPoolFactory, poolConfig);


    /**
     * 获得对象
     *
     * @return
     * @throws Exception
     */
    public static ElasticsearchClient getClient() throws Exception {
        return clientPool.borrowObject();
    }

    /**
     * 归还对象
     *
     * @param client
     */
    public static void returnClient(ElasticsearchClient client) {
        clientPool.returnObject(client);
    }
}
