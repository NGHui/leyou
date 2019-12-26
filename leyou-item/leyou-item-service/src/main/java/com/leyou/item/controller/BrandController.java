package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
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
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

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
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc) {
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        this.brandService.saveBrand(brand, cids);
        return ResponseEntity.ok().build();
    }
    //http://api.leyou.com/api/item/brand

    /**
     * 编辑完成
     *
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> editBrand(Brand brand, @RequestParam("cids") Long cids) {
        //System.out.println(brand);
        //System.out.println(cids);
        /**
         * id: 1528
         * name: NM
         * image:
         * cids: 76
         * letter: L
         */
        this.brandService.updateBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("delete/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid) {
        //http://api.leyou.com/api/item/brand/delete/1528
        //System.out.println(bid);
        this.brandService.deleteBrand(bid);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过cid查询 种类名称,手机品牌名称
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)){
            //为空
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }
}