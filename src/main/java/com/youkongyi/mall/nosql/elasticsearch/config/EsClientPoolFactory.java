package com.youkongyi.mall.nosql.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
  * @description： ES客户端连接工厂
  *     com.youkongyi.mall.nosql.elasticsearch.config.EsClientPoolFactory
  * @author： Aimer
  * @crateDate： 2022/7/13 17:41
  */
public class EsClientPoolFactory implements PooledObjectFactory<ElasticsearchClient> {

    @Override
    public PooledObject<ElasticsearchClient> makeObject() throws Exception {

        String esServerHosts = "192.168.31.195:9200";

        List<HttpHost> httpHosts = new ArrayList<>();
        //填充数据
        List<String> hostList = Arrays.asList(esServerHosts.split(","));
        for (int i = 0; i < hostList.size(); i++) {
            String host = hostList.get(i);
            httpHosts.add(new HttpHost(host.substring(0, host.indexOf(":")), Integer.parseInt(host.substring(host.indexOf(":") + 1)), "http"));
        }

        // 创建低级客户端
        RestClient restClient = RestClient.builder(httpHosts.toArray(new HttpHost[0])).build();

        //使用Jackson映射器创建传输层
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );

        ElasticsearchClient client = new ElasticsearchClient(transport);
        //log.info("对象被创建了" + client);
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(PooledObject<ElasticsearchClient> p) throws Exception {
        ElasticsearchClient elasticsearchClient = p.getObject();
        //log.info("对象被销毁了" + elasticsearchClient);
    }

    @Override
    public boolean validateObject(PooledObject<ElasticsearchClient> p) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<ElasticsearchClient> p) throws Exception {
        //log.info("对象被激活了" + p.getObject());
    }

    @Override
    public void passivateObject(PooledObject<ElasticsearchClient> p) throws Exception {
        //log.info("对象被钝化了" + p.getObject());
    }
}
