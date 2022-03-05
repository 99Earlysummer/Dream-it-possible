package com.dream.blog.controller;

import com.dream.blog.domain.dto.BannerItem;
import com.dream.blog.domain.dto.BannerQueryItem;
import com.dream.blog.domain.dto.BannerSubmitItem;
import com.dream.blog.service.BannerService;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.PageRecord;
import com.dream.common.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BannerController extends BaseController {
	@Autowired
	private  BannerService bannerService;
	@RequestMapping("/getBannerList")
	@ResponseBody
	public R getBannerList(@RequestBody BannerQueryItem query){
		PageHelper.startPage(query.getPageIndex(),query.getPageSize(),true);
		List<BannerItem> BannerList = bannerService.getBannerList(query);
		PageInfo<BannerItem> pageInfo = new PageInfo<BannerItem>(BannerList);
		long total= pageInfo.getTotal();
		int pages = pageInfo.getPages();
		PageRecord<BannerItem> pageRecord = new PageRecord<BannerItem>();
		pageRecord.setRows(BannerList);
		pageRecord.setCurrentPage(query.getPageIndex());
		pageRecord.setCurrentPageSize(query.getPageSize());
		pageRecord.setTotalCount(total);
		pageRecord.setTotalPage(pages);
		return R.success().put("data", pageRecord);
	}
	@RequestMapping("/banner/getBannerDetail/{bannerId}")
	public R getBannerDetail(@PathVariable("bannerId")int bannerId) {
		BannerItem bannerItem=bannerService.getById(bannerId);
		return R.success().put("data", bannerItem);
	}
	@RequestMapping("/banner/saveBanner")
	@ResponseBody
	public R saveBanner(@RequestBody BannerSubmitItem submitItem) {
		boolean result=false; 
		try {
			if(submitItem.getId()==null||submitItem.getId()==0)
			{
				submitItem.setCreateOn(DateUtils.getNowDate());
				submitItem.setUpdateOn(DateUtils.getNowDate());
				submitItem.setIsDeleted(0);
				result=bannerService.saveBanner(submitItem);
			}
			else {
				submitItem.setUpdateOn(DateUtils.getNowDate());
				result=bannerService.updateBanner(submitItem);
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
	@RequestMapping("/banner/deleteBanner/{bannerId}")
	@ResponseBody
	public R deleteBanner(@PathVariable("bannerId")int bannerId) {
		boolean result = false;
		try {
			result = bannerService.deleteBanner(bannerId);
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