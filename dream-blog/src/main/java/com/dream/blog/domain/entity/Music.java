package com.dream.blog.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Music implements Serializable {
	private Integer id;
    private String title; 
    private String url;
	private String singer;
	private String converUrl;
	private String totalTime;
    private Integer sortCode;
	private Date createOn;
    private Date updateOn;
}
