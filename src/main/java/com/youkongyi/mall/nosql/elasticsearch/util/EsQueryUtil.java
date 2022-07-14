package com.youkongyi.mall.nosql.elasticsearch.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;
import com.youkongyi.mall.nosql.elasticsearch.config.ESClientPool;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
  * @description： Es查询操作工具类
  *     com.youkongyi.mall.nosql.elasticsearch.util.EsQueryUtil.
  * @author： Aimer
  * @crateDate： 2022/7/14 10:55
  */
public class EsQueryUtil {

    /**
     * 查询全部数据
     *
     * @param indices
     * @return Object
     * @throws Exception
     */
    public static <T> T advancedQueryFromAllData(String indices, Class<T> type) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<T> obj = client.search(e -> e.index(indices).query(q -> q.matchAll(m -> m)), type);
        HitsMetadata<T> hits = obj.hits();
        List<Hit<T>> hits1 = hits.hits();
        ESClientPool.returnClient(client);
        return hits1.get(0).source();
    }

    /**
     * term匹配 多次匹配
     *
     * @param index
     * @param field
     * @param fieldValues
     * @return Object
     * @throws Exception
     */
    public Object advancedQueryByTerm(String index, String field, List<FieldValue> fieldValues) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> user_test = client.search(e -> e.index(index).query(q -> q.terms(t -> t.field(field).terms(terms -> terms.value(fieldValues)))).query(q -> q.matchAll(m -> m)), Object.class);
        HitsMetadata<Object> hits = user_test.hits();
        TotalHits total = hits.total();
        List<Hit<Object>> hits1 = hits.hits();
        Object source = hits1.get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * term匹配 单次匹配
     *
     * @param index
     * @param field
     * @param value
     * @return Object
     * @throws Exception
     */
    public Object advancedQueryByTerm(String index, String field, long value) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> user_test = client.search(e -> e.index(index).query(q -> q.term(t -> t.field(field).value(value))).query(q -> q.matchAll(m -> m)), Object.class);
        HitsMetadata<Object> hits = user_test.hits();
        TotalHits total = hits.total();
        List<Hit<Object>> hits1 = hits.hits();
        Object source = hits1.get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * 分页查询
     *
     * @param index
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object advancedQueryByPage(String index, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(index).query(q -> q.matchAll(m -> m)).from(from).size(size), Object.class);
//        HitsMetadata<Object> hits = searchResponse.hits();
//        List<Hit<Object>> hits1 = hits.hits();
//        Object source = hits1.get(0).source();
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;

    }

    /**
     * 排序查询
     *
     * @param index
     * @param field
     * @param order SortOrder.Desc/SortOrder.Asc
     * @return
     * @throws Exception
     */
    public Object advancedQueryBySort(String index, String field, SortOrder order) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(index).query(q -> q.matchAll(m -> m)).sort(sort -> sort.field(f -> f.field(field).order(order))), Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * filter过滤查询
     *
     * @param index
     * @param field
     * @param includes includes代表白名单，只返回指定的字段
     * @param excludes excludes代表黑名单，不返回指定的字段
     * @param order
     * @return
     * @throws Exception
     */
    public Object advancedQueryByFilter(String index, String field, String includes, String excludes, SortOrder order) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(index)
                        .query(q -> q
                                .matchAll(m -> m)
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(field)
                                        .order(order)
                                )
                        )
                        .source(source -> source
                                .filter(f -> f
                                        .includes(includes)
                                        .excludes(excludes)
                                )
                        )
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * 模糊查询
     *
     * @param index
     * @param field
     * @param value
     * @param fuzziness fuzziness代表可以与关键词有误差的字数，可选值为0、1、2这三项
     * @return
     * @throws Exception
     */
    public Object advancedQueryByLike(String index, String field, String value, String fuzziness) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(index).query(q -> q.fuzzy(f -> f.field(field).value(value).fuzziness(fuzziness))), Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * 高亮 默认黄色
     *
     * @param index
     * @param field
     * @param value
     * @return
     * @throws Exception
     */
    public Object advancedQueryByHighLight(String index, String field, String value) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(index)
                        .query(q -> q
                                .term(t -> t
                                        .field(field)
                                        .value(value)
                                )
                        )
                        .highlight(h -> h
                                .fields(field, f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * 高亮 自定义颜色
     *
     * @param index
     * @param field
     * @param value
     * @param color
     * @return
     * @throws Exception
     */
    public Object advancedQueryByHighLight(String index, String field, String value, Color color) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(index)
                        .query(q -> q
                                .term(t -> t
                                        .field(field)
                                        .value(value)
                                )
                        )
                        .highlight(h -> h
                                .fields(field, f -> f
                                        .preTags("<font color='" + color + "'>")
                                        .postTags("</font>")
                                )
                        )
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * 自定义高亮
     *
     * @param index
     * @param field
     * @param value
     * @param highlightMap
     * @return
     * @throws Exception
     */
    public Object advancedQueryByHighLight(String index, String field, String value, Map<String, HighlightField> highlightMap) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(index).query(q -> q.term(t -> t.field(field).value(value))).highlight(h -> h.fields(highlightMap)), Object.class);

        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * 极值聚合查询
     *
     * @param index
     * @param field
     * @param maxField
     * @return
     * @throws Exception
     */
    public Object advancedQueryByMax(String index, String field, String maxField) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(index).aggregations(maxField, a -> a.max(m -> m.field(field))), Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        for (Map.Entry<String, Aggregate> entry : searchResponse.aggregations().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().max().value());
        }
        return source;
    }

    /**
     * 分组查询
     *
     * @param index
     * @param field
     * @return
     * @throws Exception
     */
    public Aggregate searchStationLineChart(String index, String field) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(index).size(100).aggregations("group", a -> a.terms(t -> t.field(field))), Object.class);
        System.out.println(searchResponse.took());
        System.out.println(searchResponse.hits().total().value());
        searchResponse.hits().hits().forEach(e -> {
            System.out.println(e.source().toString());
        });
        Aggregate aggregate = searchResponse.aggregations().get("group");
        LongTermsAggregate lterms = aggregate.lterms();
        Buckets<LongTermsBucket> buckets = lterms.buckets();
        for (LongTermsBucket b : buckets.array()) {
            System.out.println(b.key() + " : " + b.docCount());
        }

        //Object source = searchResponse.hits().hits().get(0).source();
        //ESClientPool.returnClient(client);

        return aggregate;
    }

    /**
     * Term匹配
     *
     * @param indexName
     * @param field
     * @param value
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryTerm(String indexName, String field, Object value, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .term(t -> t
                                        .field(field)
                                        .value(String.valueOf(value))
                                )
                        )
                        .highlight(h -> h
                                .fields(String.valueOf(value), f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(field)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * Terms匹配
     *
     * @param indexName
     * @param field
     * @param values
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryTerms(String indexName, String field, List<FieldValue> values, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .terms(t -> t
                                        .field("field")
                                        .terms(terms -> terms
                                                .value(values)
                                        )
                                )
                        )
                        .highlight(h -> h
                                .fields(field, f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(field)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * matchAll全部
     *
     * @param indexName
     * @param field
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryMatchAll(String indexName, String field, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .matchAll(m -> m)
                        )
                        .highlight(h -> h
                                .fields(field, f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(field)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
      * @description： 多条件搜索
      *     com.youkongyi.mall.nosql.elasticsearch.util.EsQueryUtil.queryMultiMatch
      * @param： indexName (java.lang.String,java.util.List<java.lang.String)  索引名称
      * @param： fields (java.lang.String,java.lang.String,co.elastic.clients.elasticsearch._types.SortOrder,java.lang.Integer,java.lang.Integer,java.lang.Class<T>) 查询字段
      * @param： query   查询之
      * @param： sortField  排序字段
      * @param： order  排序方式
      * @param： from  查询下标
      * @param： size  查询结果大小
      * @param： clazz  转换类型
      * @return： java.util.List<T> 结果集
      * @author： Aimer
      * @crateDate： 2022/7/14 17:46
      */
    public static <T> List<T> queryMultiMatch(String indexName, List<String> fields, String query, String sortField, SortOrder order, Integer from, Integer size,Class<T> clazz) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<T> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields(fields)
                                        .query(query)
                                )
                        )
                        .highlight(h -> h
                                .fields(query, f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from -1) // Elasticsearch 下标从0开始
                        .size(size)
                , clazz);
        ESClientPool.returnClient(client);
        return searchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    }

    /**
     * matchPhrase短语匹配
     *
     * @param indexName
     * @param field
     * @param query
     * @param slop      间隔字符数量
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryMatchPhrase(String indexName, String field, String query, Integer slop, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .matchPhrase(m -> m
                                        .field(field)
                                        .query(query)
                                        .slop(slop)
                                )
                        )
                        .highlight(h -> h
                                .fields(query, f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * must必须匹配
     *
     * @param indexName
     * @param field
     * @param query
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryBoolMust(String indexName, String field, Object query, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .bool(bool -> bool
                                        .must(must -> must
                                                .match(match -> match
                                                        .field(field)
                                                        .query(String.valueOf(query))
                                                )
                                        )
                                )
                        )
                        .highlight(h -> h
                                .fields(String.valueOf(query), f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * mustNot必须不匹配
     *
     * @param indexName
     * @param field
     * @param query
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryBoolMustNot(String indexName, String field, Object query, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .bool(bool -> bool
                                        .mustNot(mustNot -> mustNot
                                                .match(match -> match
                                                        .field(field)
                                                        .query(String.valueOf(query))
                                                )
                                        )
                                )
                        )
                        .highlight(h -> h
                                .fields(String.valueOf(query), f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * should选择性匹配
     *
     * @param indexName
     * @param field
     * @param query
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryBoolShould(String indexName, String field, Object query, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .bool(bool -> bool
                                        .should(should -> should
                                                .match(match -> match
                                                        .field(field)
                                                        .query(String.valueOf(query))
                                                )
                                        )
                                )
                        )
                        .highlight(h -> h
                                .fields(String.valueOf(query), f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * filter过滤子句，必须匹配
     *
     * @param indexName
     * @param field
     * @param query
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryBoolFilter(String indexName, String field, Object query, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .bool(bool -> bool
                                        .filter(filter -> filter
                                                .match(match -> match
                                                        .field(field)
                                                        .query(String.valueOf(query))
                                                )
                                        )
                                )
                        )
                        .highlight(h -> h
                                .fields(String.valueOf(query), f -> f
                                        .preTags("<font color='yellow'>")
                                        .postTags("</font>")
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * Range范围匹配
     *
     * @param indexName
     * @param field
     * @param gt        大于
     * @param lt        小于
     * @param gte       大于等于
     * @param lte       小于等于
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryBoolRange(String indexName, String field, Object gt, Object lt, Object gte, Object lte, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e
                        .index(indexName)
                        .query(q -> q
                                .bool(b -> b
                                        .filter(f -> f
                                                .range(r -> r
                                                        .field(field)
                                                        .gt((Objects.isNull(gt) ? null : JsonData.of(gt)))
                                                        .lt((Objects.isNull(lt) ? null : JsonData.of(lt)))
                                                        .gte((Objects.isNull(gte) ? null : JsonData.of(gte)))
                                                        .lte((Objects.isNull(lte) ? null : JsonData.of(lte)))
                                                )
                                        )
                                )
                        )
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortField)
                                        .order(order)
                                )
                        )
                        .from(from)
                        .size(size)
                , Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * filter过滤
     *
     * @param indexName
     * @param excludes
     * @param includes
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryFilter(String indexName, List<String> excludes, List<String> includes, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(indexName)
                .query(q -> q.matchAll(m -> m))
                .source(s -> s.filter(f -> f.excludes(excludes)
                        .includes(includes)))
                .sort(sort -> sort.field(f -> f.field(sortField)
                        .order(order)))
                .from(from)
                .size(size), Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * fuzzy模糊匹配
     *
     * @param indexName
     * @param field
     * @param value
     * @param fuzziness
     * @param sortField
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryFuzzy(String indexName, String field, String value, String fuzziness, String sortField, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        SearchResponse<Object> searchResponse = client.search(e -> e.index(indexName)
                .query(q -> q.fuzzy(f -> f.field(field)
                        .value(value)
                        .fuzziness(fuzziness)))
                .sort(sort -> sort.field(f -> f.field(sortField)
                        .order(order)))
                .from(from)
                .size(size), Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * SearchRequest原生查询
     *
     * @param searchRequest
     * @return
     * @throws Exception
     */
    public Object queryOriginal(SearchRequest searchRequest) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        //Query of = Query.of(q -> q.bool(b -> b.must(m -> m.match(match -> match.field("").query("")))));
        SearchResponse<Object> searchResponse = client.search(searchRequest, Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }

    /**
     * Query原生查询
     *
     * @param indexName
     * @param field
     * @param query
     * @param order
     * @param from
     * @param size
     * @return
     * @throws Exception
     */
    public Object queryOriginal(String indexName, String field, Query query, SortOrder order, Integer from, Integer size) throws Exception {
        ElasticsearchClient client = ESClientPool.getClient();
        //Query of = Query.of(q -> q.bool(b -> b.must(m -> m.match(match -> match.field("").query("")))));
        SearchResponse<Object> searchResponse = client.search(e -> e.index(indexName)
                .query(query)
                .highlight(h -> h.fields(field, f -> f.preTags("<font color='yellow'>")
                        .postTags("</font>")))
                .sort(sort -> sort.field(f -> f.field(field)
                        .order(order)))
                .from(from)
                .size(size), Object.class);
        Object source = searchResponse.hits().hits().get(0).source();
        ESClientPool.returnClient(client);
        return source;
    }
}
