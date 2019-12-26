package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/16
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.groupMapper.select(specGroup);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    /**
     * 根据gid查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.paramMapper.select(record);
    }
    //改造这个方法,改造成上面的方法
    /*public List<SpecParam> queryParams(Long gid) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        return this.paramMapper.select(param);
    }*/
    /**
     * 添加分类的组
     * @param specGroup
     * @return
     */
    @Override
    public int addGroup(SpecGroup specGroup) {
        return this.groupMapper.insertSelective(specGroup);
    }
    /**
     * 删除分类的组
     * @param gid
     * @return
     */
    @Override
    public int deleteGroup(Long gid) {
        return this.groupMapper.deleteByPrimaryKey(gid);
    }

    /**
     * 编辑分类的组
     * @param specGroup
     * @return
     */
    @Override
    public int editGroup(SpecGroup specGroup) {
        return this.groupMapper.updateByPrimaryKeySelective(specGroup);
    }
}
