package com.dream.blog.service;

import com.dream.blog.domain.dto.WallpaperItem;
import com.dream.blog.domain.dto.WallpaperQueryItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WallpaperService {
	List<WallpaperItem> getWallpaperList(WallpaperQueryItem queryItem);

	WallpaperItem getById(int wallpaperId);

	WallpaperItem getRandomItem();

	boolean saveWallpaper(WallpaperItem submitItem);

	boolean updateWallpaper(WallpaperItem submitItem);

	boolean deleteWallpaper(int wallpaperId);
}
