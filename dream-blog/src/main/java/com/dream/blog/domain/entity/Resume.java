package com.dream.blog.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Resume implements Serializable {
	private Integer id;
    private String name;
    private String uuid;
    private Integer sex;
    private String birth;
    private String shengXiao;
    private String xingZhuo;
    private double height;
    private double weight;
    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String county;
    private String countyCode;
    private String school;
    private String company;
    private String phone;
    private String description;
    private String wanted;
    private String headImage;
    private String photos;
    private Integer status;
    private Date createOn;
    private Date updateOn;
}
