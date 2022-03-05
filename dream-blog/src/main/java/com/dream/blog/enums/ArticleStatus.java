
package com.dream.blog.enums;

public enum ArticleStatus {

	draft("草稿", 0),
	published("已发布", 1),
	fixedTime("定时发布", 2);

	private final String name;

	public String getName() {
		return name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	private  Integer value;

	private ArticleStatus(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
}
