package com.dream.blog.service;

import com.dream.blog.domain.entity.Music;
import com.dream.blog.domain.dto.MusicQueryItem;

import java.util.List;

public interface MusicService {
	List<Music> getMusicList(MusicQueryItem queryItem);
    Music getById(int musicId);
    boolean saveMusic(Music submitItem);
    boolean updateMusic(Music submitItem);
    boolean deleteMusic(int musicId);
}
