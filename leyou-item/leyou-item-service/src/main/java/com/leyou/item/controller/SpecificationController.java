package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询分组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid) {
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 添加分类的组
     *
     * @param specGroup
     * @return
     */
    @PostMapping("group") //@RequestBody用于接收json格式,不能将接收的参数拆分
    public ResponseEntity<Void> addGroup(@RequestBody SpecGroup specGroup) {
        //System.out.println(specGroup.getName());
        //System.out.println(specGroup.getCid());
        //添加的逻辑
        int i = this.specificationService.addGroup(specGroup);
        if (i > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        //500错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除分类的组
     * @param gid
     * @return
     */
    @DeleteMapping("group/{gid}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("gid") Long gid){
        int i = this.specificationService.deleteGroup(gid);
        if (i > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        //500错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 编辑分类的组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> editGroup(@RequestBody SpecGroup specGroup){
        int i = this.specificationService.editGroup(specGroup);
        if (i > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        //500错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 根据条件查询规格参数
     *
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    ){
        List<SpecParam> params = this.specificationService.queryParams(gid, cid, generic, searching);

        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }
    //改造这个方法,改造成上面的方法
    /*@GetMapping("params")//http://api.leyou.com/api/item/spec/params?gid=1
                        //http://api.leyou.com/api/item/spec/params?cid=76
    public ResponseEntity<List<SpecParam>> queryParams(@RequestParam("gid") Long gid) {
        List<SpecParam> params = this.specificationService.queryParams(gid);
        if (CollectionUtils.isEmpty(params)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }*/


}