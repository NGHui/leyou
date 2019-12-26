package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import com.leyou.item.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

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
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);
        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional //事务回滚
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        // 先新增brand
        this.brandMapper.insertSelective(brand);
        // 在新增中间表
        cids.forEach(cid->{
            this.brandMapper.insertBrandAndCategory(cid,brand.getId()); });
    }

    /**
     * 商品修改
     *
     * @param brand
     * @param cid
     */
    @Transactional
    @Override
    public void updateBrand(Brand brand, Long cid) {
        //先修改商品表
        this.brandMapper.updateByPrimaryKey(brand);
        //再修改中间表
        this.brandMapper.updateBrandAndCategory(cid,brand.getId());

    }

    /**
     * 删除分类
     * @param id
     */
    @Transactional
    @Override
    public void deleteBrand(Long id) {
        //先删除商品表
        this.brandMapper.deleteByPrimaryKey(id);
        //再删除中间表
        this.brandMapper.deleteBrandAndCategory(id);

    }

    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        //通过中间表查询手机品牌信息
        return this.brandMapper.selectBrandByCid(cid);
    }
}
