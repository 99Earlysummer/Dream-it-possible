package com.dream.blog.mapper;

import com.dream.blog.domain.dto.ResumeItem;
import com.dream.blog.domain.dto.ResumeQueryItem;
import com.dream.blog.domain.dto.ResumeShareItem;
import com.dream.blog.domain.dto.ResumeSubmitItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResumeMapper {

	List<ResumeItem> getList(ResumeQueryItem queryItem);

	ResumeItem getByUuid(@Param("uuid") String uuid);

	ResumeShareItem getByShareCode(@Param("gid") String gid);

	boolean saveResume(ResumeSubmitItem submitItem);

	boolean updateResume(ResumeSubmitItem submitItem);

	boolean updateResumeShareCode(@Param("uuid") String uuid,@Param("gid") String gid);

	boolean deleteResume(@Param("uuid") String uuid);
}
