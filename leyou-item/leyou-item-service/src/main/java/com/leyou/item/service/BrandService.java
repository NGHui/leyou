package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
public interface BrandService {

    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    void saveBrand(Brand brand, List<Long> cids);

    /**
     * 商品修改
     *
     * @param brand
     * @param cid
     */
    void updateBrand(Brand brand, Long cid);

    /**
     * 删除分类
     *
     * @param id
     */
    void deleteBrand(Long id);


    /**
     *
     * @param cid
     * @return
     */
    List<Brand> queryBrandsByCid(Long cid);
}
