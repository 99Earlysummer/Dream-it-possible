package com.dream.blog.service.impl;

import com.dream.blog.domain.dto.WallpaperItem;
import com.dream.blog.domain.dto.WallpaperQueryItem;
import com.dream.blog.mapper.WallPaperMapper;
import com.dream.blog.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WallpaperServiceImpl implements WallpaperService {

    @Autowired
    private WallPaperMapper wallpaperMapper;

	@Override
	public List<WallpaperItem> getWallpaperList(WallpaperQueryItem queryItem) {
		return wallpaperMapper.getWallpaperList(queryItem);
	}

	
	@Override
	public WallpaperItem getById(int wallpaperId) {
		return wallpaperMapper.getById(wallpaperId);
	}

	@Override
	public WallpaperItem getRandomItem() {
		return wallpaperMapper.getRandomItem();
	}

	@Override
	public boolean saveWallpaper(WallpaperItem submitItem) {
		return wallpaperMapper.saveWallpaper(submitItem);
	}

	@Override
	public boolean updateWallpaper(WallpaperItem submitItem) {
		return wallpaperMapper.updateWallpaper(submitItem);
	}

	@Override
	public boolean deleteWallpaper(int wallpaperId) {
		return wallpaperMapper.deleteWallpaper(wallpaperId);
	}
	
}
