package com.dream.blog.controller;

import com.dream.blog.domain.dto.WallpaperItem;
import com.dream.blog.domain.dto.WallpaperQueryItem;
import com.dream.blog.service.WallpaperService;
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
public class WallpaperController extends BaseController {
	@Autowired
	private  WallpaperService wallpaperService;
	@RequestMapping("/getWallpaperList")
	public R getWallpaperList(@RequestBody WallpaperQueryItem query){
		PageHelper.startPage(query.getPageIndex(),query.getPageSize(),true);
		List<WallpaperItem> WallpaperList = wallpaperService.getWallpaperList(query);
		PageInfo<WallpaperItem> pageInfo = new PageInfo<WallpaperItem>(WallpaperList);
		long total= pageInfo.getTotal();
		int pages = pageInfo.getPages();
		PageRecord<WallpaperItem> pageRecord = new PageRecord<WallpaperItem>();
		pageRecord.setRows(WallpaperList);
		pageRecord.setCurrentPage(query.getPageIndex());
		pageRecord.setCurrentPageSize(query.getPageSize());
		pageRecord.setTotalCount(total);
		pageRecord.setTotalPage(pages);
		return R.success().put("data", pageRecord);
	}
	@RequestMapping("/wallpaper/getWallpaperDetail/{wallpaperId}")
	public R getWallpaperDetail(@PathVariable("wallpaperId")int wallpaperId) {
		WallpaperItem wallpaperItem=wallpaperService.getById(wallpaperId);
		return R.success().put("data", wallpaperItem);
	}
	@RequestMapping("/wallpaper/saveWallpaper")
	public R saveWallpaper(@RequestBody WallpaperItem submitItem) {
		boolean result=false; 
		try {
			if(submitItem.getId()==null||submitItem.getId()==0){
				submitItem.setCreateOn(DateUtils.getNowDate());
				submitItem.setUpdateOn(DateUtils.getNowDate());
				submitItem.setIsDeleted(0);
				result=wallpaperService.saveWallpaper(submitItem);
			}
			else {
				submitItem.setUpdateOn(DateUtils.getNowDate());
				result=wallpaperService.updateWallpaper(submitItem);
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
	@RequestMapping("/wallpaper/deleteWallpaper/{wallpaperId}")
	public R deleteWallpaper(@PathVariable("wallpaperId")int wallpaperId) {
		boolean result = false;
		try {
			result = wallpaperService.deleteWallpaper(wallpaperId);
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