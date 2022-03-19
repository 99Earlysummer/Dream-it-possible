package com.dream.blog.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResumeItem implements Serializable {
    private String uuid;
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

    public void initRegionItem() {
        List<Map<String, String>> items=new ArrayList<>();
        Map<String,String> provinceItem=new HashMap<>();
        provinceItem.put("name",province);
        provinceItem.put("code",provinceCode);
        items.add(provinceItem);
        Map<String,String> cityItem=new HashMap<>();
        cityItem.put("name",city);
        cityItem.put("code",cityCode);
        items.add(cityItem);
        Map<String,String> countyItem=new HashMap<>();
        countyItem.put("name",county);
        countyItem.put("code",countyCode);
        items.add(countyItem);
        this.setRegionItem(items);
    }

    private List<Map<String,String>> regionItem;
}
