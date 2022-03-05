package com.dream.blog.domain.vo;

import com.dream.blog.domain.entity.ArticleFile;
import com.dream.blog.domain.entity.Category;
import com.dream.blog.domain.entity.Tag;
import com.dream.blog.enums.ArticleStatus;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class ArticleVoItem {
	private Integer id;
    private String title;   //标题
    private String abstractContent; //摘要内容
    private String publishTime; //显示时间
	private List<HashMap<String,String>> categoryItems; //主题分类
    private List<Tag>  articleTags; //文章标签
    private Integer showStyle;  //展示样式
	private List<String> coverImageList; //封面图片
    private Integer openComment; //打开评论
    private Integer isRecommend; //是否推荐
    private String author; //作者
    private Integer viewCount;  //浏览数
    private Category category;
	private String content;

}
