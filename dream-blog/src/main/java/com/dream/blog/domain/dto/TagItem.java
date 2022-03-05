package com.dream.blog.domain.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class TagItem implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private int id;
	public String name;
	public String color;
	public String alia;
}
