package com.dream.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dream.blog.domain.entity.Tag;
import com.dream.blog.domain.dto.TagItem;
import com.dream.blog.mapper.TagMapper;
import com.dream.blog.service.TagService;
import com.dream.common.constant.RedisConstant;
import com.dream.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	RedisCache redisCache;
    @Autowired
    private TagMapper tagMapper;
	@Override
	public List<Tag> getAll() {
		String tagJson = redisCache.getCacheObject(RedisConstant.tag_list);
		if("nothing".equals(tagJson)) {
			return new ArrayList<>();
		}
		List<Tag> cacheList=JSON.parseObject(tagJson,new TypeReference<List<Tag>>(){});
		if(cacheList==null||cacheList.size()==0){
			List<Tag> val=tagMapper.getAll();
			if(val==null||val.size()==0){//缓存击穿，
				redisCache.setCacheObject(RedisConstant.tag_list, "nothing",30, TimeUnit.SECONDS);
				return new ArrayList<>();
			}else {
				redisCache.setCacheObject(RedisConstant.tag_list, JSON.toJSONString(val),3600, TimeUnit.SECONDS);
				return val;
			}
		}else {
			return cacheList;
		}
	}

	@Override
	public List<Tag> getTagByIds(List<String> tagIds) {
		return tagMapper.getTagByIds(tagIds);
	}

	@Override
	public List<Tag> getTagByName(String tagName) {
		return tagMapper.getTagByName(tagName);
	}

	@Override
	public int saveTag(TagItem submitItem) {
		return tagMapper.saveTag(submitItem);
	}

	@Override
	public boolean updateTag(TagItem submitItem) {
		return tagMapper.updateTag(submitItem);
	}

	@Override
	public boolean deleteTag(int tagId) {
		return tagMapper.deleteTag(tagId);
	}

	
}
