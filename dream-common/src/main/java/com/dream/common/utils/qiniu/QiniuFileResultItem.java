package com.dream.common.utils.qiniu;

import lombok.Data;

import java.util.List;

@Data
public class QiniuFileResultItem {
    private String marker;
    private List<QiniuFileModel> items;
}
