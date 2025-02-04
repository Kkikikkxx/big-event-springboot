package com.kkikikk.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;

/**
 * 阿里云OSS工具类
 */
@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    // 阿里云OSS的终端节点
    private String endpoint;
    // 阿里云OSS的访问密钥ID
    private String accessKeyId;
    // 阿里云OSS的访问密钥密钥
    private String accessKeySecret;
    // 阿里云OSS的存储桶名称
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes 文件的字节数组
     * @param objectName 文件在OSS中的对象名称
     * @return 文件上传后的访问URL
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            // 处理OSS异常
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            // 处理客户端异常
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            // 关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 构建文件访问路径
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        // 记录日志
        log.info("文件上传到:{}", stringBuilder);

        // 返回文件访问URL
        return stringBuilder.toString();
    }
}
