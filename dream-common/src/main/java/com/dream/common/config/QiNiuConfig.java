package com.dream.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author dream
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfig
{
    private static  String accesskey;

    private static String secretKey;

    private static String bucketName;

    private static String bucketHostName;

    private static String prefixName;

    public static String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        QiNiuConfig.accesskey = accesskey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        QiNiuConfig.secretKey = secretKey;
    }

    public static String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        QiNiuConfig.bucketName = bucketName;
    }

    public static String getBucketHostName() {
        return bucketHostName;
    }

    public void setBucketHostName(String bucketHostName) {
        QiNiuConfig.bucketHostName = bucketHostName;
    }

    public static String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        QiNiuConfig.prefixName = prefixName;
    }



}
