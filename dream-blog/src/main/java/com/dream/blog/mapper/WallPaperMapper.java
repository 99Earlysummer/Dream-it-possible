package com.dream.blog.mapper;


import com.dream.blog.domain.dto.WallpaperItem;
import com.dream.blog.domain.dto.WallpaperQueryItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WallPaperMapper {
    List<WallpaperItem> getWallpaperList(WallpaperQueryItem queryItem);
    WallpaperItem getById(@Param("wallpaperId") int wallpaperId);
    WallpaperItem getRandomItem();
    boolean saveWallpaper(WallpaperItem submitItem);
    boolean updateWallpaper(WallpaperItem submitItem);
    boolean deleteWallpaper(@Param("wallpaperId") int wallpaperId);
}
