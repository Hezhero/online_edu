package com.onlineedu.eduoss.controller;

import com.aliyun.oss.OSSClient;
import com.onlineedu.common.R;
import com.onlineedu.eduoss.service.OssService;
import com.onlineedu.eduoss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/eduoss/oss")
public class OSSController {
    @Autowired
    private OssService ossService;
    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file){
      String url = ossService.uploadFile(file);
        System.out.println(url);
       return R.ok().data("url",url);
    }
}
