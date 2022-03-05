package com.dream.blog.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileList implements Serializable {
	private Integer id;
    private String key;
    private String hash;
    private String fsize;
    private String mimeType;
    private String putTime;
    private String fsiurlze;
    private String url;
    private String scope;
    private String suffix;

}
