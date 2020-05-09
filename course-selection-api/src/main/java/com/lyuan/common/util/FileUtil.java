package com.lyuan.common.util;

import com.lyuan.common.define.ConfigDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Autowired
    ConfigDefine configDefine;

    //保存图片
    public String saveImage(MultipartFile file){
        String suffic = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String fileName = UUID.randomUUID()+suffic;
        String imageSavePath = configDefine.getImageSavePath();
        File savePath = new File(imageSavePath);
        if (!savePath.exists()){
            savePath.mkdirs();
        }
        File saveFile = new File(imageSavePath+fileName);
        try {
            file.transferTo(saveFile);
            String fileUrl = configDefine.getImageVisitPath()+fileName;
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String fileName){
        File file = new File(configDefine.getImageSavePath()+fileName);
        file.delete();
    }
}
