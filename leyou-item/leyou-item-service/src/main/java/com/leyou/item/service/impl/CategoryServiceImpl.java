package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据parentId查询子类目
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    @Override
    public List<Category> queryByBrandId(Long bid) {
        return categoryMapper.queryByBrandId(bid);
    }

    @Transactional
    @Override
    public int addCategory(Category category) {
        System.out.println(category.getParentId());
        List<Category> categories = this.categoryMapper.queryAllByParentId(category.getParentId());
        System.out.println("集合长度:"+categories.size());
        if (categories.size()==0){
            //查出单个
            /*id: 0
            name: 新的节点
            parentId: 1508
            isParent: false
            sort: 1*/
            //保存当前的节点
            this.categoryMapper.insertSelective(category);
            //没有父节,修改查出的节点为父节点
            return this.categoryMapper.updateCategoryIsParent(1,category.getParentId());
        }else{
            //查出集合
            /*id: 0
            name: 新的节点
            parentId: 74
            isParent: false
            sort: 6*/
            return this.categoryMapper.insertSelective(category);
        }
    }

    @Override
    public int editCategory(Long id, String name) {
        return this.categoryMapper.updateCategory(id,name);
    }

    @Override
    public Category queryCategory(Long pid) {
        return this.categoryMapper.selectByPrimaryKey(pid);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        int index = 0;
        //先查看该节点是否是父节点
        Category category = this.categoryMapper.selectByPrimaryKey(id);
        if (category.getIsParent()) {
            //是,先删除父节点,在删除子节点
            //删除父节点
            this.categoryMapper.deleteByPrimaryKey(id);
            //删除子节点
            this.categoryMapper.deleteByParentId(id);
        }else {
            //否,直接删除.
             this.categoryMapper.deleteByPrimaryKey(id);
        }
    }
}
