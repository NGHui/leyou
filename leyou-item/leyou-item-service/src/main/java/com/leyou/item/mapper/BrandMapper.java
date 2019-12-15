package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     *根据商品id,修改分类的id
     * @param cid 分类id
     * @param bid 商品id
     * @return
     */
    @Update("update tb_category_brand cb set cb.category_id=#{cid} where cb.brand_id=#{bid}")
    int updateBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 删除中间表
     * @param bid
     * @return
     */
    @Delete("delete from tb_category_brand where brand_id=#{bid}")
    int deleteBrandAndCategory(@Param(value = "bid") Long bid);
}