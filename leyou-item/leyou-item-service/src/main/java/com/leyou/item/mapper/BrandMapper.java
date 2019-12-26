package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增商品分类和品牌中间表数据
     *
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 根据商品id,修改分类的id
     *
     * @param cid 分类id
     * @param bid 商品id
     * @return
     */
    @Update("update tb_category_brand cb set cb.category_id=#{cid} where cb.brand_id=#{bid}")
    int updateBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 删除中间表
     *
     * @param bid
     * @return
     */
    @Delete("delete from tb_category_brand where brand_id=#{bid}")
    int deleteBrandAndCategory(@Param(value = "bid") Long bid);

    /**
     * 通过中间表查询商品品牌信息
     * @param cid 内链接,需要两张表中同时所有的字段,才可以查出相应的字段
     * @return
     */
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);
}