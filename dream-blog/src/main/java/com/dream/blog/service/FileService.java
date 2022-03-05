package com.dream.blog.service;

import com.dream.blog.domain.entity.FileList;

import java.util.List;

public interface FileService {
	List<FileList> getAll();
	List<FileList> getByKeys(List<String> keys);
	List<FileList> getByKey(String key);
	int saveFile(FileList submitItem);
}
