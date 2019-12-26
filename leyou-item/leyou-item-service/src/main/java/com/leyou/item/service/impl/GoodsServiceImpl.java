package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.Stock;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/17
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SkuMapper skuMapper;

    /*@Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 1.搜索条件
        if (StringUtils.isBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        //2.是否上架
        if (saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        // 3.分页条件
        PageHelper.startPage(page,rows);
        // 4.执行查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> info = new PageInfo<>(spus);
        //5.把list<Spu> 转化为List<SpuBo>
        List<SpuBo> collect = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);
            //获取品牌对象
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            //设置品牌名称
            spuBo.setBname(brand.getName());
            List<String> strings = categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            //和分类的名称
            spuBo.setCname(StringUtils.join(strings,"-"));

            return spuBo;
        }).collect(Collectors.toList());
        //返回结分页结果集
        return new PageResult<>(info.getTotal(), collect);
    }*/

    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 搜索条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        // 分页条件
        PageHelper.startPage(page, rows);
        // 执行查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
        List<SpuBo> spuBos = new ArrayList<>();
        //遍历集合,将一个集合转化为另一个集合
        spus.forEach(spu -> {
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);
            // 查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            // 查询品牌的名称
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });
        return new PageResult<>(pageInfo.getTotal(), spuBos);
    }

    /**
     * 添加商品,需要同时添加四张表中的字段,才可以组成商品的所有的信息
     * @param spuBo
     */
    @Transactional
    @Override
    public void saveGoods(SpuBo spuBo) {
        /*brandId: 1528
        cid1: 74
        cid2: 75
        cid3: 76
        skus: [{price: 2000, stock: "99", indexes: "0_0_0", enable: true, title: "heima a a a",…},…]
        spuDetail: {packingList: "aaaa", afterService: "aaaaa", description: "",…}
        subTitle: "aaaa"
        title: "heima "*/
        //1. 新增spu
        // 设置默认字段
        spuBo.setId(null);//id自增长
        spuBo.setSaleable(true);//是否为上架
        spuBo.setValid(true);//删除标记状态
        spuBo.setCreateTime(new Date());//创建时间
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        //2.新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.insertSelective(spuDetail);
        //3.新增sku
        //4.新增库存
    }

    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * 根据spuId查询sku的集合
     * @param spuId
     * @return
     */
    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(s -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }



    @Transactional
    @Override
    public void updateGoods(SpuBo spuBo){
        // 查询以前sku
        List<Sku> skus = this.querySkusBySpuId(spuBo.getId());
        // 如果以前存在，则删除
        if(!CollectionUtils.isEmpty(skus)) {
            List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            // 删除以前库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);

            // 删除以前的sku
            Sku record = new Sku();
            record.setSpuId(spuBo.getId());
            this.skuMapper.delete(record);

        }
        // 新增sku和库存
        saveSkuAndStock(spuBo);

        // 更新spu
        spuBo.setLastUpdateTime(new Date());
        spuBo.setCreateTime(null);
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);

        // 更新spu详情
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }


    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

}
