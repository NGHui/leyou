package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/16
 */
public interface SpecificationService {

    /**
     * 根据分类id查询分组
     *
     * @param cid
     * @return
     */
    List<SpecGroup> queryGroupsByCid(Long cid);

    /**
     * 根据条件查询规格参数
     *
     * @param gid
     * @return
     */
    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
    //方法被改造
    //List<SpecParam> queryParams(Long gid);

    /**
     * 添加分类的组
     *
     * @param specGroup
     * @return
     */
    int addGroup(SpecGroup specGroup);

    /**
     * 删除分类的组
     *
     * @param gid
     * @return
     */
    int deleteGroup(Long gid);

    /**
     * 编辑分类的组
     *
     * @param specGroup
     * @return
     */
    int editGroup(SpecGroup specGroup);


}
