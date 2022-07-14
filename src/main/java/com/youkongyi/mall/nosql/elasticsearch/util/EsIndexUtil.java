package com.youkongyi.mall.nosql.elasticsearch.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.IntegerNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteAliasResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetAliasResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.elasticsearch.indices.UpdateAliasesResponse;
import co.elastic.clients.elasticsearch.indices.get_alias.IndexAliases;
import com.youkongyi.mall.nosql.elasticsearch.config.ESClientPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
  * @description： Es索引操作工具类
  *     com.youkongyi.mall.nosql.elasticsearch.util.EsIndexUtil
  * @author： Aimer
  * @crateDate： 2022/7/14 10:34
  */
public class EsIndexUtil {

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public static boolean createIndex(String indexName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        //创建索引并返回状态
        CreateIndexResponse createIndexResponse = client.indices().create(c -> c.index(indexName));
        boolean acknowledged = createIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
        ESClientPool.returnClient(client);
        return acknowledged;
    }

    /**
     * 创建索引 指定映射
     *
     * @param indexName
     * @param documentMap
     * @return
     * @throws Exception
     */
    public static boolean createIndex(String indexName, Map<String, Property> documentMap) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        if(exists(client, indexName)){
            return false;
        }
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexBuilder -> createIndexBuilder.index(indexName).mappings(mappings -> mappings.properties(documentMap))
                //.aliases("User", aliases -> aliases.isWriteIndex(true))
        );
        boolean acknowledged = createIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
        ESClientPool.returnClient(client);
        return acknowledged;
    }

    /**
     * 删除索引
     */
    public static boolean deleteIndex(String indexName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        boolean exists = exists(client, indexName);
        if (!exists) {
            //不存在就结束
            return false;
        }
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(index -> index.index(indexName));
        boolean acknowledged = deleteIndexResponse.acknowledged();
        ESClientPool.returnClient(client);
        System.out.println("acknowledged = " + acknowledged);
        return acknowledged;

    }

    /**
     * 删除索引 批量
     */
    public static boolean deleteIndex(List<String> indexName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        boolean exists = exists(client, indexName);
        if (!exists) {
            //不存在就结束
            return false;
        }
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(index -> index.index(indexName));
        boolean acknowledged = deleteIndexResponse.acknowledged();
        ESClientPool.returnClient(client);
        System.out.println("acknowledged = " + acknowledged);
        return acknowledged;
    }

    /**
     * 查看索引信息
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public static Map<String, IndexState> getIndexMsg(String indexName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        GetIndexResponse getIndexResponse = client.indices().get(getIndex -> getIndex.index(indexName));
        Map<String, IndexState> result = getIndexResponse.result();
        ESClientPool.returnClient(client);
        return result;
    }

    /**
     * 查看所有索引信息
     *
     * @return
     * @throws Exception
     */
    public static List<IndicesRecord> getAllIndex() throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        IndicesResponse indicesResponse = client.cat().indices();
        ESClientPool.returnClient(client);
        return indicesResponse.valueBody();
    }

    /**
     * 索引是否存在
     *
     * @param client
     * @param indexName
     * @return
     */
    public static boolean exists(ElasticsearchClient client, String indexName) throws Exception {
        boolean value = client.indices().exists(e -> e.index(indexName)).value();
        System.out.println("indexexists:  " + value);
        return value;
    }

    public static boolean exists(ElasticsearchClient client, List<String> indexName) throws Exception {
        boolean value = client.indices().exists(e -> e.index(indexName)).value();
        System.out.println("indexexists:  " + value);
        return value;
    }

    /**
     * 添加别名
     *
     * @param indexName 索引名称
     * @param aliasName 别名名称
     */
    public static boolean addAliases(List<String> indexName, String aliasName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        UpdateAliasesResponse updateAliasesResponse = client.indices().updateAliases(update -> update.actions(action -> action.add(add -> add.indices(indexName).alias(aliasName))));
        return updateAliasesResponse.acknowledged();
    }

    /**
     * 移除别名
     *
     * @param indexName
     * @param aliasName
     */
    public static boolean removeAliases(List<String> indexName, String aliasName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        DeleteAliasResponse deleteAliasResponse = client.indices().deleteAlias(del -> del.index(indexName).name(aliasName));
        ESClientPool.returnClient(client);
        return deleteAliasResponse.acknowledged();
    }

    /**
     * 重命名别名，解除旧索引的别名，填加新索引的别名
     *
     * @param indexName
     * @param newAliasName
     * @param oldAliasName
     */
    public static boolean renameAliases(List<String> indexName, String newAliasName, String oldAliasName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        DeleteAliasResponse deleteAliasResponse = null;
        if (StringUtils.isNotBlank(oldAliasName)) {
            deleteAliasResponse = client.indices().deleteAlias(del -> del.index(indexName).name(oldAliasName));
        }
        if (!deleteAliasResponse.acknowledged()) return false;
        UpdateAliasesResponse updateAliasesResponse = client.indices().updateAliases(update -> update.actions(action -> action.add(add -> add.indices(indexName).alias(newAliasName))));
        ESClientPool.returnClient(client);
        return updateAliasesResponse.acknowledged();
    }

    /**
     * 根据别名查询索引信息
     *
     * @param aliasName
     */
    public static Map<String, IndexAliases> getIndexMsgByAlias(String aliasName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        GetAliasResponse getAliasResponse = client.indices().getAlias(a -> a.name(aliasName));
        Map<String, IndexAliases> result = getAliasResponse.result();
        return result;
    }

    /**
     * 根据别名查询索引名称
     *
     * @param aliasName
     */
    public static List<String> getIndexListByAlias(String aliasName) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        GetAliasResponse getAliasResponse = client.indices().getAlias(a -> a.name(aliasName));
        Map<String, IndexAliases> result = getAliasResponse.result();
        List<String> indexList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            indexList = result.keySet().stream().collect(Collectors.toList());
        }
        return indexList;
    }
}
