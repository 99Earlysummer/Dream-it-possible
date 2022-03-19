package com.dream.blog.service.impl;

import com.dream.blog.domain.dto.ResumeItem;
import com.dream.blog.domain.dto.ResumeQueryItem;
import com.dream.blog.domain.dto.ResumeShareItem;
import com.dream.blog.domain.dto.ResumeSubmitItem;
import com.dream.blog.mapper.ResumeMapper;
import com.dream.blog.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

	@Override
	public List<ResumeItem> getList(ResumeQueryItem queryItem) {
		List<ResumeItem> list=resumeMapper.getList(queryItem);
		return list;
	}

	
	@Override
	public ResumeItem getByUuid(String uuid) {
		ResumeItem item= resumeMapper.getByUuid(uuid);
		item.initRegionItem();
		return item;
	}

	@Override
	public ResumeShareItem getByShareId(String gid) {
		ResumeShareItem item= resumeMapper.getByShareCode(gid);
		return item;
	}

	@Override
	public boolean saveResume(ResumeSubmitItem submitItem) {
		return resumeMapper.saveResume(submitItem);
	}

	@Override
	public boolean updateResume(ResumeSubmitItem submitItem) {
		return resumeMapper.updateResume(submitItem);
	}

	@Override
	public boolean updateResumeShareCode(String uuid, String gid) {
		return resumeMapper.updateResumeShareCode(uuid,gid);
	}

	@Override
	public boolean deleteResume(String uuid) {
		return resumeMapper.deleteResume(uuid);
	}
	
}
