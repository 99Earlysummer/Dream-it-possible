package com.dream.blog.service;

import com.dream.blog.domain.entity.ArticleDetail;
import com.dream.blog.domain.entity.ArticleFile;
import com.dream.blog.domain.dto.ArticleItem;
import com.dream.blog.domain.dto.ArticleQueryItem;
import com.dream.blog.domain.dto.ArticleSubmitItem;
import com.dream.blog.domain.vo.ArticleVoItem;
import com.dream.common.utils.PageRecord;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    PageRecord<ArticleVoItem> getArticleList(ArticleQueryItem queryItem);
    List<ArticleItem> getArticlePage(ArticleQueryItem queryItem);
    List<ArticleItem> getRecommendArticleList();
    List<ArticleItem> getTopReadArticleList();
    List<Map<String,Object>> getRelvantArticle(Integer articleId);
    List<Integer> getNoAbstractArticleId();
    ArticleItem getById(int articleId);
    ArticleDetail getContentById(int articleId);
    List<ArticleFile> getFileById(int articleId);
    int saveArticle(ArticleSubmitItem submitItem);
    void saveContent(ArticleDetail submitItem);
    void saveImage(List<ArticleFile> fileList);
    boolean updateArticle(ArticleSubmitItem submitItem);
    void updateAbstractById(int articleId, String abstractContent);
    boolean deleteArticle(int articleId);
    boolean deleteImage(int articleId);
    boolean deleteContent(int articleId);
    void updateArticleViewCount(int articleId, int viewCount);
    void updateArticleTags(int articleId, String articleTags);
}
