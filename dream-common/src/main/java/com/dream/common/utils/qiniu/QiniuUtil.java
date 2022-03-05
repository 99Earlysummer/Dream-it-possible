package com.dream.common.utils.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
@Component
@Configuration
public class QiniuUtil  {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${qiniu.accesskey}")
    private String accesskey;

    @Value("${qiniu.secretkey}")
    private String secretKey;

    @Value("${qiniu.bucketname}")
    private String bucketName;

    @Value("${qiniu.buckethostname}")
    private String bucketHostName;

    @Value("${qiniu.prefixName}")
    private String prefixName;

    private Auth auth;

    private UploadManager uploadManager = null;
    private BucketManager bucketManager;

    @Bean
    public void  init() {
        this.bucketHostName = bucketHostName;
        this.bucketName = bucketName;
        this.auth = auth;
        //构造一个带指定Zone对象的配置类
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.zone0());
        this.uploadManager = new UploadManager(cfg);
        this.bucketManager = new BucketManager(auth, cfg);
        this.auth=Auth.create(this.accesskey, this.secretKey);
    }

    public String generate() {
        return this.generateToken();
    }


    /**
     * 根据spring mvc 文件接口上传
     *
     * @param multipartFile spring mvc 文件接口
     * @return 文件路径
     * @throws IOException
     */
    
    public String uploadFile(MultipartFile multipartFile) throws FileUploadException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(bytes);


    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param filePath      文件前缀,例如:/test或者/test/
     * @param multipartFile spring mvc 文件接口
     * @return 文件路径
     * @throws IOException
     */
    
    public String uploadFile(String filePath, MultipartFile multipartFile) throws FileUploadException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(filePath, bytes);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param multipartFile spring mvc 文件接口
     * @param fileName      文件名
     * @return 文件路径
     * @throws IOException
     */
    
    public String uploadFile(MultipartFile multipartFile, String fileName) throws FileUploadException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(bytes, fileName);
    }


    /**
     * 根据spring mvc 文件接口上传
     *
     * @param multipartFile spring mvc 文件接口
     * @param fileName      文件名
     * @param filePath      文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws IOException
     */
    
    public String uploadFile(MultipartFile multipartFile, String fileName, String filePath) throws FileUploadException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(bytes, fileName, filePath);
    }


    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file 文件
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(File file) throws FileUploadException {
        return this.uploadFile(file, null, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file     文件
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(String filePath, File file) throws FileUploadException {
        return this.uploadFile(file, null, filePath);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file     文件
     * @param fileName 文件名
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(File file, String fileName) throws FileUploadException {
        return this.uploadFile(file, fileName, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file     文件
     * @param fileName 文件名
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(File file, String fileName, String filePath) throws FileUploadException {
        String key = preHandle(fileName, filePath);
        Response response = null;
        try {
            response = this.uploadManager.put(file, key, this.generateToken());
        } catch (QiniuException e) {
            LOGGER.warn("QiniuException:", e);
            throw new FileUploadException(e.getMessage());
        }
        return this.getUrlPath(response);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data 文件
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(byte[] data) throws FileUploadException {
        return this.uploadFile(data, null, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data     文件
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(String filePath, byte[] data) throws FileUploadException {
        return this.uploadFile(data, null, filePath);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data     文件
     * @param fileName 文件名
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(byte[] data, String fileName) throws FileUploadException {
        return this.uploadFile(data, fileName, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data     文件
     * @param fileName 文件名
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws FileUploadException
     */
    
    public String uploadFile(byte[] data, String fileName, String filePath) throws FileUploadException {
        String key = preHandle(fileName, filePath);
        Response response;
        try {
            response = this.uploadManager.put(data, key, generateToken());
        } catch (QiniuException e) {
            LOGGER.error("QiniuException:", e);
            throw new FileUploadException(e.getMessage());
        }
        return this.getUrlPath(response);
    }

    private byte[] getBytesWithMultipartFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String preHandle(String fileName, String filePath) throws FileUploadException {
        if (StringUtils.isNotBlank(fileName) && !fileName.contains(".")) {
            throw new FileUploadException("文件名必须包含尾缀");
        }
        if (StringUtils.isNotBlank(filePath) && !filePath.startsWith("/")) {
            throw new FileUploadException("前缀必须以'/'开头");
        }
        String name = StringUtils.isBlank(fileName) ? RandomStringUtils.randomAlphanumeric(32) : fileName;
        if (StringUtils.isBlank(filePath)) {
            return name;
        }
        String prefix = filePath.replaceFirst("/", "");
        return (prefix.endsWith("/") ? prefix : prefix.concat("/")).concat(name);
    }

    private String generateToken() {
        return this.auth.uploadToken(bucketName);
    }


    private String getUrlPath(Response response) throws FileUploadException {
        if (!response.isOK()) {
            throw new FileUploadException("文件上传失败");
        }
        DefaultPutRet defaultPutRet;
        try {
            defaultPutRet = response.jsonToObject(DefaultPutRet.class);
        } catch (QiniuException e) {
            LOGGER.warn("QiniuException", e);
            throw new FileUploadException(e.getMessage());
        }
        String key = defaultPutRet.key;
        if (key.startsWith(bucketHostName)) {
            return key;
        }
        return bucketHostName + (key.startsWith("/") ? key : "/" + key);
    }

    
    public QiniuFileResultItem getFileList(String marker, int limit) {
        QiniuFileResultItem result = new QiniuFileResultItem();
        List<QiniuFileModel> res = new ArrayList<>();
        FileListing fileList = new FileListing();
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        if (limit <= 0) limit = 100;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        try {
            fileList = bucketManager.listFiles(this.bucketName, prefix, marker, limit, delimiter);
            result.setMarker(fileList.marker);
            if (fileList != null && fileList.items.length > 0) {
                //处理获取的file list结果
                for (FileInfo item : fileList.items) {
                    QiniuFileModel mo = new QiniuFileModel();
                    mo.setKey(item.key);
                    mo.setHash(item.hash);
                    mo.setFsize(item.fsize);
                    mo.setMimeType(item.mimeType);
                    mo.setPutTime(item.putTime);
                    mo.setEndUser(item.endUser);
                    mo.setUrl(String.format("http://%s/%s", this.bucketHostName, item.key));
                    res.add(mo);
                }
            }
            result.setItems(res);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean deleteByKey(String key) {
        try {
            Response res = bucketManager.delete(this.bucketName, key);
            if (res.statusCode == 200) {
                return true;
            }
            return false;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return false;
    }
}
