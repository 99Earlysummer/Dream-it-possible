
package com.dream.blog.domain.dto;


import com.dream.common.utils.BaseQueryItem;

public class BannerQueryItem  extends BaseQueryItem {
	private String title;
	private String sortCode;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
}
