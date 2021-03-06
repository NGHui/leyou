package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
public interface CategoryService {

    /**
     * 根据parentId查询子类目
     *
     * @param pid
     * @return
     */
    List<Category> queryCategoriesByPid(Long pid);

    /**
     * 根据商品的id查询分类信息
     *
     * @param bid
     * @return
     */
    List<Category> queryByBrandId(Long bid);

    /**
     * 添加新的分类
     *
     * @param category
     * @return
     */
    int addCategory(Category category);

    /**
     * 编辑分类
     *
     * @param id
     * @param name
     * @return
     */
    int editCategory(Long id, String name);

    /**
     * 通过pid查看数据库中是否有数据
     *
     * @param pid
     * @return
     */
    Category queryCategory(Long pid);

    /**
     * 通过id删除商品类目
     *
     * @param id
     */
    void deleteCategory(Long id);

    /**
     * 通过id查询分类集合
     * @param ids
     * @return
     */
    List<String> queryNamesByIds(List<Long> ids);
}
