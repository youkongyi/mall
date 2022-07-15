package com.youkongyi.mall.service.impl;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.mapper.EsProductMapper;
import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import com.youkongyi.mall.nosql.elasticsearch.util.EsConstant;
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
        List<BulkResponseItem> res = EsDocumentUtil.bulkInsert(EsConstant.INDEX_NAME_PMS, esProductList);
        return res.size();
    }

    @Override
    public void delete(String id) throws Exception {
        EsDocumentUtil.deleteByQuery(EsConstant.INDEX_NAME_PMS, "id", id);
    }

    @Override
    public ReturnDTO<EsProduct> create(Long id) throws Exception {
        ReturnDTO<EsProduct> returnDTO = new ReturnDTO<>();
        List<EsProduct> esProductList = productMapper.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            EsDocumentUtil.createDocument(EsConstant.INDEX_NAME_PMS, esProduct);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(esProduct);
        }
        return returnDTO;
    }

    @Override
    public void delete(List<String> ids) throws Exception {
        EsDocumentUtil.delDocByIds(EsConstant.INDEX_NAME_PMS, ids.toArray(new String[0]));
    }

    @Override
    public List<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) throws Exception {
        int from = (pageNum-1) * pageSize +1;
        return EsQueryUtil.queryMultiMatch(EsConstant.INDEX_NAME_PMS, Arrays.asList("name", "subTitle"), keyword, "id", SortOrder.Desc, from, pageSize, EsProduct.class);
    }
}
