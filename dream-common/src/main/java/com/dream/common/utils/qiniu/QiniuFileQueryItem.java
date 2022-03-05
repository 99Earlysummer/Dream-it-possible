package com.dream.common.utils.qiniu;

import lombok.Data;

@Data
public class QiniuFileQueryItem {
    private String marker;
    private int limit;
    private String prefix;
    private String delimiter;
}
