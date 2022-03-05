package com.dream.common.utils.qiniu;

import lombok.Data;

@Data
public class QiniuFileModel {
    private String key;
    private String hash;
    private long fsize;
    private String mimeType;
    private long putTime;
    private String endUser;
    private String url;
}
