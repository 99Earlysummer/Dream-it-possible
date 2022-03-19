package com.dream.blog.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResumeShareItem implements Serializable {
    private String gid;
    private String name;
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

}
