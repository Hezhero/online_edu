package com.onlineedu.eduoss.service.impl;

import com.aliyun.oss.OSSClient;
import com.onlineedu.common.R;
import com.onlineedu.eduoss.service.OssService;
import com.onlineedu.eduoss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.ServiceMode;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            //2.8.3
            OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            //添加uuid值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //添加filename里面  01.jpg
            filename = uuid+filename;


            //调用方法上传
            // 01.jpg
            //    /2019/09/12/01.jpg
            //获取当前日期，转换 /2019/09/12/
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            filename = timeUrl+"/"+filename;

            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //返回上传之后url地址
            //https://edu-demo0422.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        }catch(Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
