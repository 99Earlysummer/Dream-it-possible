package com.dream.blog.mapper;


import com.dream.blog.domain.entity.Music;
import com.dream.blog.domain.dto.MusicQueryItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MusicMapper {
    List<Music> getMusicList(MusicQueryItem queryItem);
    Music getById(@Param("musicId") int musicId);
    boolean saveMusic(Music submitItem);
    boolean updateMusic(Music submitItem);
    boolean deleteMusic(@Param("musicId") int musicId);
}
