package com.dream.blog.controller;

import com.dream.blog.domain.dto.ResumeItem;
import com.dream.blog.domain.dto.ResumeQueryItem;
import com.dream.blog.domain.dto.ResumeShareItem;
import com.dream.blog.domain.dto.ResumeSubmitItem;
import com.dream.blog.service.ResumeService;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.PageRecord;
import com.dream.common.utils.R;
import com.dream.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class AboutController extends BaseController {
	@Autowired
	private  ResumeService resumeService;
	@RequestMapping("/getList")
	@ResponseBody
	public R getList(@RequestBody ResumeQueryItem query){
		PageHelper.startPage(query.getPageIndex(),query.getPageSize(),true);
		List<ResumeItem> ResumeList = resumeService.getList(query);
		PageInfo<ResumeItem> pageInfo = new PageInfo<ResumeItem>(ResumeList);
		long total= pageInfo.getTotal();
		int pages = pageInfo.getPages();
		PageRecord<ResumeItem> pageRecord = new PageRecord<ResumeItem>();
		pageRecord.setRows(ResumeList);
		pageRecord.setCurrentPage(query.getPageIndex());
		pageRecord.setCurrentPageSize(query.getPageSize());
		pageRecord.setTotalCount(total);
		pageRecord.setTotalPage(pages);
		return R.success().put("data", pageRecord);
	}
	@RequestMapping("/getDetail")
	public R getDetail(@Param("uuid") String uuid) {
		ResumeItem detail = resumeService.getByUuid(uuid);
		return R.success().put("data", detail);
	}
	@RequestMapping("/getShareData")
	public R getShareData(@Param("gid") String gid) {
		ResumeShareItem detail = resumeService.getByShareId(gid);
		return R.success().put("data", detail);
	}
	@RequestMapping("/saveResume")
	@ResponseBody
	public R saveResume(@RequestBody ResumeSubmitItem submitItem) {
		boolean result=false; 
		try {
			submitItem.initRegionItem();
			ResumeItem item = resumeService.getByUuid(submitItem.getUuid());
			if(item==null){
				submitItem.setCreateOn(DateUtils.getNowDate());
				submitItem.setUpdateOn(DateUtils.getNowDate());
				result=resumeService.saveResume(submitItem);
			}
			else {
				submitItem.setUpdateOn(DateUtils.getNowDate());
				result=resumeService.updateResume(submitItem);
			}
			
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
		}
		if(result)
		{
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
	@RequestMapping("/deleteResume/{uuid}")
	@ResponseBody
	public R deleteResume(@Param("uuid")String uuid) {
		boolean result = false;
		try {
			result = resumeService.deleteResume(uuid);
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
		}
		if (result) {
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
	@RequestMapping("/updateResumeShareCode")
	@ResponseBody
	public R updateResumeShareCode(@Param("uuid")String uuid,@Param("gid")String gid) {
       		boolean result = false;
		try {
			result = resumeService.updateResumeShareCode(uuid,gid);
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
		}
		if (result) {
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
}