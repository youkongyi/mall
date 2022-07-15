package com.youkongyi.mall.nosql.elasticsearch.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.youkongyi.mall.nosql.elasticsearch.config.ESClientPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description： Es文档操作工具类
 * com.youkongyi.mall.nosql.elasticsearch.util.EsDocumentUtil
 * @author： Aimer
 * @crateDate： 2022/7/14 10:34
 */
public class EsDocumentUtil {

    /**
     * 添加文档信息
     *
     * @param indexName
     * @param obj
     */
    public static long createDocument(String indexName, Object obj) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        IndexResponse indexResponse = client.index(x -> x.index(indexName).document(obj));
        long version = indexResponse.version();
        ESClientPool.returnClient(client);
        return version;
    }

    /**
     * 添加文档信息 指定id
     *
     * @param indexName
     * @param obj
     */
    public static long createDocument(String indexName, String id, Object obj) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        IndexResponse indexResponse = client.index(x -> x.index(indexName).id(id).document(obj));
        long version = indexResponse.version();
        ESClientPool.returnClient(client);
        return version;
    }

    /**
     * 修改文档自定义属性
     *
     * @param indexName
     * @param id
     * @param obj
     * @return version
     */
    public static long updateDocument(String indexName, String id, Object obj) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        UpdateResponse<Object> userUpdateResponse = client.update(x -> x.index(indexName).id(id).doc(obj), Object.class);
        long version = userUpdateResponse.version();
        ESClientPool.returnClient(client);
        return version;
    }

    /**
     * bulk批量插入
     *
     * @param indexName
     * @param objList
     * @return List<BulkResponseItem>
     * @throws Exception
     */
    public static <T> List<BulkResponseItem> bulkInsert(String indexName, List<T> objList) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        //创建BulkOperation列表准备批量插入doc
        List<BulkOperation> bulkOperations = new ArrayList<>();
        //将user中id作为es id，也可不指定id es会自动生成id
        objList.forEach(doc -> bulkOperations.add(BulkOperation.of(b -> b.index(c -> c.document(doc)))));
        BulkResponse bulk = client.bulk(x -> x.index(indexName).operations(bulkOperations));
        List<BulkResponseItem> items = bulk.items();
        ESClientPool.returnClient(client);
        return items;
    }

    /**
     * bulk批量插入 指定id
     *
     * @param indexName
     * @param idList    id顺序要与实体数据顺序对应
     * @param objList
     * @return List<BulkResponseItem>
     * @throws Exception
     */
    public static List<BulkResponseItem> bulkInsert(String indexName, List<String> idList, List<Object> objList) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        //创建BulkOperation列表准备批量插入doc
        List<BulkOperation> bulkOperations = new ArrayList<>();
        //将id作为es id，也可不指定id es会自动生成id
        for (int i = 0; i < objList.size(); i++) {
            int finalI = i;
            bulkOperations.add(BulkOperation.of(b -> b.index(c -> c.id(idList.get(finalI)).document(objList.get(finalI)))));
        }
        BulkResponse bulk = client.bulk(x -> x.index(indexName).operations(bulkOperations));
        List<BulkResponseItem> items = bulk.items();
        ESClientPool.returnClient(client);
        return items;
    }

    /**
     * bulk批量删除文档记录
     *
     * @param indexName
     * @param documentId
     * @return List<BulkResponseItem>
     * @throws Exception
     */
    public static List<BulkResponseItem> delDocByIds(String indexName, String... documentId) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        // 构建批量操作对象BulkOperation的集合
        List<BulkOperation> bulkOperations = new ArrayList<>();
        // 向集合中添加需要删除的文档id信息
        for (String doc : documentId) {
            bulkOperations.add(BulkOperation.of(b -> b.delete(d -> d.index(indexName).id(doc))));
        }
        // 调用客户端的bulk方法，并获取批量操作响应结果
        BulkResponse response = client.bulk(e -> e.index(indexName).operations(bulkOperations));
        ESClientPool.returnClient(client);
        return response.items();
    }

    /**
      * @description： 根据传入的字段删除文档
      *     com.youkongyi.mall.nosql.elasticsearch.util.EsDocumentUtil.deleteByQuery
      * @param： indexName (java.lang.String) 索引名称
     * @param： field (java.lang.String) 字段名
     * @param： productIds (java.lang.String...) 字段值
      * @author： Aimer
      * @crateDate： 2022/7/15 15:25
      */
    public static Long deleteByQuery(String indexName,String field, String... productIds) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        List<FieldValue> fieldValues = Arrays.stream(productIds).map(FieldValue::of).collect(Collectors.toList());
        DeleteByQueryResponse res = client.deleteByQuery(del -> del
                .index(indexName)
                .query(q -> q
                        .terms(t -> t
                                .field(field)
                                .terms(te -> te
                                        .value(fieldValues)
                                )
                        )
                )
        );
        ESClientPool.returnClient(client);
        return res.deleted();
    }

    /**
     * bluk批量更新数据
     *
     * @param indexName
     * @param idList
     * @param objList
     * @return List<BulkResponseItem> items
     * @throws Exception
     */
    public static List<BulkResponseItem> bulkUpdate(String indexName, List<String> idList, List<Object> objList) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        //创建BulkOperation列表准备批量插入doc
        List<BulkOperation> bulkOperations = new ArrayList<>();
        //将id作为es id，也可不指定id es会自动生成id
        for (int i = 0; i < objList.size(); i++) {
            int finalI = i;
            //TODO 没测试不知对不对
            bulkOperations.add(BulkOperation.of(b -> b.update(u -> u.index(indexName).id(idList.get(finalI)).action(a -> a.doc(objList.get(finalI))))));
        }
        BulkResponse bulk = client.bulk(x -> x.index(indexName).operations(bulkOperations));
        List<BulkResponseItem> items = bulk.items();
        ESClientPool.returnClient(client);
        return items;
    }
}
