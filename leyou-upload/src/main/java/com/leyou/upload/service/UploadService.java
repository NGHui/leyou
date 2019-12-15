package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/12/13
 */
public interface UploadService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
