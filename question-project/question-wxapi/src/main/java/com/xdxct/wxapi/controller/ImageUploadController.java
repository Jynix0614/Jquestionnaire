package com.xdxct.wxapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author 姜瑜欣
 * @description: 图片上传控制器
 * @date 2022/1/15 11:19
 */
@RestController
@RequestMapping("/api/upload/")
public class ImageUploadController {
    //图片上传路径
    @Value("${web.uploadpath}")
    private String webUploadPath;

    @RequestMapping("/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file){
        String url = null;
        //获取名字
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtenionName = fileName.substring(fileName.indexOf("."));
        //生成新名字
        String newName = UUID.randomUUID().toString() + fileExtenionName;
        String path = webUploadPath;
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,newName);
        try{
            file.transferTo(targetFile);
            url = "/" + targetFile.getName();
        }catch (Exception e){
            return null;
        }
        return "/images"+ url;
    }

}
