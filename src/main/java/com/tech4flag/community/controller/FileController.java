package com.tech4flag.community.controller;

import com.tech4flag.community.dto.FileDTO;
import com.tech4flag.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-18 23:37
 */
@Controller
public class FileController {
    @Autowired
    private UCloudProvider ucloudProvider;

    @RequestMapping("/file/upload")
    public @ResponseBody FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest mul =(MultipartHttpServletRequest) request;
        MultipartFile multipartFile =mul.getFile("editormd-image-file");
        try {
            String fileName = ucloudProvider.upload(multipartFile.getInputStream(), multipartFile.getContentType(), multipartFile.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功");
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("成功");
        fileDTO.setUrl("/community/img/timg.jpeg");

        return fileDTO;
    }
}
