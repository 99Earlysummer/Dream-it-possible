
package com.dream.blog.domain.dto;


import com.dream.common.utils.BaseQueryItem;
import lombok.Data;

@Data
public class ResumeQueryItem extends BaseQueryItem {
	private String name;
	private Integer sex;
	public Integer startAge;
	public Integer endAge;
}
