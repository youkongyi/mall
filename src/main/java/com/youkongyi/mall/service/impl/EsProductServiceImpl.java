package com.youkongyi.mall.service.impl;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.mapper.EsProductMapper;
import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import com.youkongyi.mall.nosql.elasticsearch.repository.EsProductRepository;
import com.youkongyi.mall.service.IEsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
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

    private final EsProductRepository productRepository;

    @Autowired
    public EsProductServiceImpl(EsProductMapper productMapper, EsProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }


    @Override
    public int importAll() throws Exception {
        List<EsProduct> esProductList = productMapper.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(String id) throws Exception {
        productRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public ReturnDTO<EsProduct> create(Long id) throws Exception {
        ReturnDTO<EsProduct> returnDTO = new ReturnDTO<>();
        List<EsProduct> esProductList = productMapper.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            EsProduct result = productRepository.save(esProduct);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(result);
        }
        return returnDTO;
    }

    @Override
    public void delete(List<String> ids) throws Exception {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsProduct> esProductList = new ArrayList<>();
            for (String id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(Long.valueOf(id));
                esProductList.add(esProduct);
            }
            productRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
