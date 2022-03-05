package com.dream.blog.domain.dto;

import com.dream.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WallpaperItem{
	private Integer id;
    private String title;
    private String imgSrc;
    private Integer isDeleted;
	private Date createOn;   
    private Date updateOn;
}
