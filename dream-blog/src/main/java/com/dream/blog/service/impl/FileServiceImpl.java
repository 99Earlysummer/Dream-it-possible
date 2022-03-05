package com.dream.blog.service.impl;

import com.dream.blog.domain.entity.FileList;
import com.dream.blog.mapper.FileMapper;
import com.dream.blog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;


	@Override
	public List<FileList> getAll() {
		return fileMapper.getAll();
	}

	@Override
	public List<FileList> getByKeys(List<String> keys) {
		return fileMapper.getByKeys(keys);
	}

	@Override
	public List<FileList> getByKey(String key) {
		return fileMapper.getByKey(key);
	}

	@Override
	public int saveFile(FileList submitItem) {
		return fileMapper.saveFile(submitItem);
	}
}
