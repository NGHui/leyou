package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/11
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父id查询子节点
     *
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam("pid") Long pid) {

        if (pid == null || pid.longValue() < 0) {
            // 响应400，相当于ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(categories)) {
            // 响应404
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * 通过品牌id查询商品分类
     *
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid) {
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 添加新的节点
     *
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<Void> addCategory(Category category) {
        System.out.println(category.getId());
        System.out.println(category.getParentId());
        int i = this.categoryService.addCategory(category);
        if (i > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("edit") //@RequestParam中的name 和 value 没有区别
    public ResponseEntity<Void> editCategory(@RequestParam(value = "id") Long id, @RequestParam(name = "name") String name) {
        this.categoryService.editCategory(id, name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") Long id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
    /*cid: 76
        generic: true
        groupId: 1
        name: "aaa"
        numeric: true
        searching: true
        segments: "0-0"
        unit: "aaa"*/



}