package com.dream.blog.service;

import com.dream.blog.domain.entity.Tag;
import com.dream.blog.domain.dto.TagItem;

import java.util.List;

public interface TagService {
	List<Tag> getAll();
	List<Tag> getTagByIds(List<String> tagIds);
	List<Tag> getTagByName(String tagName);
	int saveTag(TagItem submitItem);
	boolean updateTag(TagItem submitItem);
	boolean deleteTag(int tagId);
}
