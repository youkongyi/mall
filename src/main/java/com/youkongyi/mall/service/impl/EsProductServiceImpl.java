package com.youkongyi.mall.service.impl;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.youkongyi.mall.mapper.EsProductMapper;
import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import com.youkongyi.mall.nosql.elasticsearch.util.EsDocumentUtil;
import com.youkongyi.mall.nosql.elasticsearch.util.EsQueryUtil;
import com.youkongyi.mall.service.IEsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
  * @description： 商品搜索管理Service实现类
  *     com.youkongyi.mall.service.impl.EsProductServiceImpl.
  * @author： Aimer
  * @crateDate： 2022/7/8 15:14
  */
@Slf4j
@Service
public class EsProductServiceImpl implements IEsProductService {

    private final EsProductMapper productMapper;

    @Autowired
    public EsProductServiceImpl(EsProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    @Override
    public int importAll() throws Exception {
        List<EsProduct> esProductList = productMapper.getAllEsProductList(null);
        List<BulkResponseItem> res = EsDocumentUtil.bulkInsert("pms", esProductList);
        return res.size();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public EsProduct create(Long id) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public List<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) throws Exception {
        int from = (pageNum-1) * pageSize +1;
//        ElasticsearchClient client = ESClientPool.getClient();
//        SearchResponse<EsProduct> res = client.search(s -> s.index("pms")
//                .query(q -> q
//                        .multiMatch(m -> m
//                                .fields("name", "subTitle")
//                                .query(keyword)))
//                        .from(from - 1)// elasticsearch下标从0开始
//                        .size(pageSize)
//                        .sort(o -> o
//                                .field(order -> order
//                                        .field("id")
//                                        .order(SortOrder.Desc)))
//                , EsProduct.class);
//        ESClientPool.returnClient(client);
//        List<Hit<EsProduct>> hits = res.hits().hits();
//        return hits.stream().map(Hit::source).collect(Collectors.toList());
        return EsQueryUtil.queryMultiMatch("pms", Arrays.asList("name", "subTitle"), keyword, "id", SortOrder.Desc, from, pageSize, EsProduct.class);
    }
}
