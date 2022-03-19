package com.dream.blog.service;

import com.dream.blog.domain.dto.ResumeItem;
import com.dream.blog.domain.dto.ResumeQueryItem;
import com.dream.blog.domain.dto.ResumeShareItem;
import com.dream.blog.domain.dto.ResumeSubmitItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResumeService {
	List<ResumeItem> getList(ResumeQueryItem queryItem);

	ResumeItem getByUuid(String uuid);

	ResumeShareItem getByShareId(String gid);

	boolean saveResume(ResumeSubmitItem submitItem);

	boolean updateResume(ResumeSubmitItem submitItem);

	boolean updateResumeShareCode(String uuid,String gid);

	boolean deleteResume(String uuid);
}
