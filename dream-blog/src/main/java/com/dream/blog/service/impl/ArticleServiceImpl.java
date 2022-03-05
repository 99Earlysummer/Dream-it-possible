package com.dream.blog.service.impl;

import com.dream.blog.domain.entity.ArticleDetail;
import com.dream.blog.domain.entity.ArticleFile;
import com.dream.blog.domain.dto.ArticleItem;
import com.dream.blog.domain.dto.ArticleQueryItem;
import com.dream.blog.domain.dto.ArticleSubmitItem;
import com.dream.blog.domain.entity.Tag;
import com.dream.blog.domain.vo.ArticleVoItem;
import com.dream.blog.mapper.ArticleMapper;
import com.dream.blog.service.ArticleService;
import com.dream.blog.service.TagService;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.PageRecord;
import com.dream.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dream.common.utils.bean.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
	@Autowired
	private TagService tagService;
	@Override
	public PageRecord<ArticleVoItem> getArticleList(ArticleQueryItem queryItem) {
		List<ArticleVoItem> voItems=new ArrayList<>();
		List<ArticleItem> list=articleMapper.getArticleList(queryItem);
		PageInfo<ArticleItem> pageInfo = new PageInfo<ArticleItem>(list);
		long total = pageInfo.getTotal();
		int pages = pageInfo.getPages();
		PageRecord<ArticleVoItem> pageRecord = new PageRecord<ArticleVoItem>();
		if(list!=null&&list.size()>0){
			List<Tag> tagList = tagService.getAll();
			voItems = list.stream().map(n -> {
				ArticleVoItem item = new ArticleVoItem();
				String articleTags = n.getArticleTags();
				BeanUtils.copyProperties(n, item);
				if (StringUtils.isNotEmpty(articleTags)) {
					List<String> aTagList = new ArrayList<>(Arrays.asList(articleTags.split(",")));
					List<Tag> tags = tagList.stream().filter(m -> aTagList.contains(m.getId().toString())).collect(Collectors.toList());
					item.setArticleTags(tags);
				}
				List<ArticleFile> coverImageList = n.getCoverImageList();
				if(coverImageList!=null&&coverImageList.size()>0){
					List<String> covers = coverImageList.stream().map(ArticleFile::getCoverImage).collect(Collectors.toList());
					item.setCoverImageList(covers);
				}
				item.setPublishTime(n.getPublishTime().substring(0,10));
				return item;
			}).collect(Collectors.toList());
		}
		pageRecord.setRows(voItems);
		pageRecord.setCurrentPage(queryItem.getPageIndex());
		pageRecord.setCurrentPageSize(queryItem.getPageSize());
		pageRecord.setTotalCount(total);
		pageRecord.setTotalPage(pages);
		return pageRecord;
	}

	@Override
	public List<ArticleItem> getArticlePage(ArticleQueryItem queryItem) {
		List<ArticleItem> list=articleMapper.getArticleList(queryItem);
		return list;
	}

	@Override
	public ArticleItem getById(int articleId) {
		return articleMapper.getById(articleId);
	}

	@Override
	public ArticleDetail getContentById(int articleId) {
		return articleMapper.getContentById(articleId);
	}

	@Override
	public List<ArticleFile> getFileById(int articleId) {
		return articleMapper.getImageById(articleId);
	}


	@Override
	public int saveArticle(ArticleSubmitItem submitItem) {
		return articleMapper.saveArticle(submitItem);
	}

	@Override
	public void saveContent(ArticleDetail submitItem) {
		articleMapper.saveContent(submitItem);
	}

	@Override
	public void saveImage(List<ArticleFile> fileList) {
		articleMapper.saveImage(fileList);
	}

	@Override
	public boolean updateArticle(ArticleSubmitItem submitItem) {
		return articleMapper.updateArticle(submitItem);
	}

	@Override
	public void updateAbstractById(int articleId, String abstractContent) {
		articleMapper.updateAbstractById(articleId,abstractContent);
	}

	@Override
	public boolean deleteArticle(int articleId) {
		return articleMapper.deleteArticle(articleId);
	}

	@Override
	public boolean deleteImage(int articleId) {
		return articleMapper.deleteImage(articleId);
	}

	@Override
	public boolean deleteContent(int articleId) {
		return articleMapper.deleteContent(articleId);
	}

	@Override
	public void updateArticleViewCount(int articleId, int viewCount) {
		 articleMapper.updateArticleViewCount(articleId,viewCount);
	}

	@Override
	public void updateArticleTags(int articleId, String articleTags) {
		articleMapper.updateArticleTags(articleId,articleTags);
	}

	@Override
	public List<ArticleItem> getRecommendArticleList() {
		// TODO Auto-generated method stub
		return articleMapper.getRecommendArticleList();
	}

	@Override
	public List<ArticleItem> getTopReadArticleList() {
		// TODO Auto-generated method stub
		return articleMapper.getTopReadArticleList();
	}

	@Override
	public List<Map<String, Object>> getRelvantArticle(Integer articleId) {
		ArticleItem articleItem=articleMapper.getById(articleId);
		if(articleItem!=null&& StringUtils.isNotEmpty(articleItem.getArticleTags())){
			List<String> tids=Arrays.asList(articleItem.getArticleTags().split(","));
			String StrTagId= tids.stream().map(n->String.format("'%s'",n)).collect(Collectors.joining(","));
			return articleMapper.getRelvantArticle(articleId,StrTagId);
		}
		return null;
	}

	@Override
	public List<Integer> getNoAbstractArticleId() {
		return articleMapper.getNoAbstractArticleId();
	}


}
