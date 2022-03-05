package com.dream.blog.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Banner implements Serializable {
	private Integer id;
    private String title; 
    private String imgSrc;
    private String subTitle;
    private String author;
    private String linkUrl;
    private Integer sortCode; 
    private Integer isDeleted;
	private Date createOn;   
    private Date updateOn;  
    private Integer isShow;
}
