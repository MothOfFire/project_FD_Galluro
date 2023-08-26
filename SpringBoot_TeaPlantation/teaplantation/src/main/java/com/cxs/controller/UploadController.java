package com.cxs.controller;

import com.cxs.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class UploadController {

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file) {
        //获取文件名称
        String originalFilename = file.getOriginalFilename();
        if (file != null) {
            //获得项目的类路径
            String path = "E:\\Sources\\Code\\SpringBoot_TeaPlantation\\teaweb\\src\\assets\\img\\";
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
//                System.out.println("上传头像路径不存在，正在创建...");
                uploadDir.mkdir();
            }
            //我的文件保存在static目录下的
            File picture = new File(path, originalFilename);
            try {
                //保存图片
                file.transferTo(picture);
                String picture1 = "/img/" + originalFilename;
                return Result.suc(picture1);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.fail("图片保存失败");
            }
        } else {
            return Result.fail("没有接受到图片文件");
        }
    }

}
