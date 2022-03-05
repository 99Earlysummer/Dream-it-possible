package com.dream.blog.service.impl;

import com.dream.blog.domain.entity.Music;
import com.dream.blog.domain.dto.MusicQueryItem;
import com.dream.blog.mapper.MusicMapper;
import com.dream.blog.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

	@Override
	public List<Music> getMusicList(MusicQueryItem queryItem) {
		return musicMapper.getMusicList(queryItem);
	}

	
	@Override
	public Music getById(int bannerId) {
	
		return musicMapper.getById(bannerId);
	}

	@Override
	public boolean saveMusic(Music submitItem) {
		return musicMapper.saveMusic(submitItem);
	}

	@Override
	public boolean updateMusic(Music submitItem) {
		return musicMapper.updateMusic(submitItem);
	}

	@Override
	public boolean deleteMusic(int bannerId) {
		return musicMapper.deleteMusic(bannerId);
	}
	
}
