package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
@Repository //操作分类
public interface CategoryMapper extends Mapper<Category> {

    /**
     * 根据品牌id查询商品分类
     * @param bid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(@Param("bid") Long bid);


    @Update("update tb_category set name=#{name} where id=#{id}")
    int updateCategory(@Param("id") Long id, @Param("name") String name);

    /**
     * 修改节点是否为父节点
     * @return
     */
   @Update("update tb_category set is_parent=#{isParent} where id=#{id}")
    int updateCategoryIsParent(@Param("isParent") Integer isParent,@Param("id") Long id);

   @Select("SELECT * FROM tb_category c WHERE c.parent_id=#{pid}")
    List<Category> queryAllByParentId(@Param("pid") Long pid);

   @Delete("delete from tb_category where parent_id=#{pid}")
    int deleteByParentId(@Param("pid") long pid);
}
