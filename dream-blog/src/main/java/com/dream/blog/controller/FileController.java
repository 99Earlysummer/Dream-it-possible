package com.dream.blog.controller;

import com.dream.blog.domain.entity.FileList;
import com.dream.blog.service.FileService;
import com.dream.common.config.DreamAppConfig;
import com.dream.common.utils.file.FileUtils;
import com.dream.common.utils.file.MimeTypeUtils;
import com.dream.common.utils.qiniu.QiniuUtil;
import com.dream.framework.config.ServerConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
public class FileController extends BaseController {
    @Autowired
    QiniuUtil qiniuUtil;
    @Autowired
    FileService fileService;
    @Autowired
    private ServerConfig serverConfig;
    @PostMapping("/qiNiu/upload")
    @ResponseBody
    public HashMap<String, Object> upload(HttpServletRequest req, HttpServletResponse response) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        List<HashMap<String, Object>> fre = new ArrayList<HashMap<String, Object>>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");
        // 上传文件路径
        String filePath = DreamAppConfig.getBlogUploadPath();
        try {
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                //String fileName = FileUploadUtils.upload(filePath, file,null);//允許所有類型的文件
                //String url = serverConfig.getUrl() + fileName;
                String fileName=file.getOriginalFilename();
                String urlStr = qiniuUtil.uploadFile(filePath, file);//允許所有類型的文件
                String url = "http://" + urlStr;
                HashMap<String, Object> fileResult = new HashMap<>();
                if (StringUtils.isNotBlank(url)) {
                    fileResult.put("msg", "上传成功");
                    fileResult.put("name", fileName);
                    fileResult.put("status", 0);
                    fileResult.put("url", url);
                    fre.add(fileResult);
                    //追加文件记录表
                    String[] arr=fileName.split("\\.");
                    FileList submitItem=new FileList();
                    submitItem.setFsize(String.valueOf(file.getSize()));
                    submitItem.setKey(arr[0]);
                    submitItem.setHash(fileName);
                    submitItem.setSuffix(arr[1]);
                    String mimeType=MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
                    submitItem.setMimeType(mimeType);
                    submitItem.setScope("blog");
                    submitItem.setUrl(url);
                    fileService.saveFile(submitItem);
                }
            }
            response.setHeader("X-Frame-Options", "SAMEORIGIN");// 解决IFrame拒绝的问题
            res.put("success", 1);
            res.put("msg", "ok");
            res.put("result", fre);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    @RequestMapping("/qiNiu/batchUpload")
    @ResponseBody
    public HashMap<String, Object> batchUpload(@RequestParam(value = "file", required = true) MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();
        List<HashMap<String, Object>> fre = new ArrayList<HashMap<String, Object>>();
        for (MultipartFile file : files) {
            String url = "http://" +  qiniuUtil.uploadFile(file);
            HashMap<String, Object> fileResult = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(url)) {
                fileResult.put("msg", "上传成功");
                fileResult.put("name", file.getOriginalFilename());
                fileResult.put("status", 0);
                fileResult.put("url", url);
                fre.add(fileResult);
            }
        }
        response.setHeader("X-Frame-Options", "SAMEORIGIN");// 解决IFrame拒绝的问题
        res.put("success", 1);
        res.put("msg", "ok");
        res.put("result", fre);
        return res;

    }

    //文件下载相关代码  ,图片直接打开，附件下载
    @GetMapping(value = "/file/{fileName}")
    public void downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<FileList> fileList = fileService.getByKey(fileName);
        if (fileList != null && fileList.size() > 0) {
            String mimeType = fileList.get(0).getMimeType();
            String suffix = fileList.get(0).getSuffix();
            String filePath = DreamAppConfig.getBlogFilePath() + fileName+"."+suffix;
            if(StringUtils.isBlank(suffix)){
                filePath = DreamAppConfig.getBlogFilePath() + fileName;
            }
            response.setContentType(fileList.get(0).getMimeType());
            //只有png，jpg，gif设置直接打开，其他文件下载
            if (!mimeType.equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)
                    && !mimeType.equalsIgnoreCase(MediaType.IMAGE_GIF_VALUE)
                    && !mimeType.equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE)) {
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                FileUtils.setAttachmentResponseHeader(response, fileName);
            }
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            Map<String, String> map = new HashMap<>();
            map.put("status", "error");
            map.put("msg", "找不到该文件");
            writer.write(map.toString());
        }

    }
}