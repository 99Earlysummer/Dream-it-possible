package com.dream.blog.domain.dto;

import com.dream.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class BannerItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer serialNumber;
	private Integer id;
    private String title;
	private String subTitle;
    private String author;
    private String imgSrc;
    private String linkUrl;
    private Integer sortCode; 
    private Integer isDeleted; 
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCreateOn() {
        return  DateUtils.parseDateToStr("yyyy-MM-dd", this.createOn);
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public String getUpdateOn() {
		return  DateUtils.parseDateToStr("yyyy-MM-dd", this.updateOn);
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
		
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	/**
	 * @return the serialNumber
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	private Date createOn;   
    private Date updateOn;  
    private Integer isShow;
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
