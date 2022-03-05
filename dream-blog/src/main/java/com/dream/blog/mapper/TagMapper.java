package com.dream.blog.mapper;


import com.dream.blog.domain.entity.Tag;
import com.dream.blog.domain.dto.TagItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagMapper {
    List<Tag> getAll();
    List<Tag> getTagByIds(@Param("tagIds") List<String> tagIds);
    List<Tag> getTagByName(@Param("tagName") String tagName);
    int saveTag(TagItem submitItem);
	boolean updateTag(TagItem submitItem);
	boolean deleteTag(@Param("tagId") int tagId);
}
