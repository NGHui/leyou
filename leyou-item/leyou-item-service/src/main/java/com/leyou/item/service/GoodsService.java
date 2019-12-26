package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/17
 */
public interface GoodsService {

    /**
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 添加商品信息
     * @param spuBo
     * @return
     */
    void saveGoods(SpuBo spuBo);

    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 根据spuId查询sku的集合
     * @param spuId
     * @return
     */
    List<Sku> querySkusBySpuId(Long spuId);

    void updateGoods(SpuBo spuBo);
}
