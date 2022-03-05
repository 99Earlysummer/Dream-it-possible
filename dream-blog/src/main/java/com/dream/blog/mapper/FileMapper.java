package com.dream.blog.mapper;


import com.dream.blog.domain.entity.FileList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileMapper {
    List<FileList> getAll();
    List<FileList> getByKeys(@Param("keys") List<String> keys);
    List<FileList> getByKey(@Param("key") String key);
    int saveFile(FileList submitItem);
}
