package com.dream.common.utils.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Tan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestQuery {
    /**
     * 索引库名称
     */
    private String esIndex;
    /**
     * 搜索条件
     */
    private String keyword;
    /**
     *全文检索的字段名称
     */
    private String[] keywordFields;
    /**
     *
     */
    private Integer pageNo;
    /**
     *
     */
    private Integer pageSize;
    /**
     *排序字段
     */
    private String sortBy;
    /**
     *是否降序
     */
    private Boolean isDesc;
    /**
     *过滤关系，key为（and或者or或者not），value为过滤字段和值
     */
    private Map<String,Object> filter;
    /**
     *时间字段
     */
    private String dateField;
    /**
     *开始时间
     */
    private String startDate;
    /**
     *结束时间
     */
    private String endDate;
    /**
     *显示字段
     */
    private String[] sourceFilter;
    /**
     *聚合集合
     */
    private LinkedHashMap<String,String> aggMap;
}
