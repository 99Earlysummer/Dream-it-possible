package com.dream.blog.controller;

import com.dream.blog.domain.dto.*;
import com.dream.blog.domain.entity.ArticleDetail;
import com.dream.blog.domain.entity.ArticleFile;
import com.dream.blog.domain.entity.Tag;
import com.dream.blog.domain.vo.ArticleVoItem;
import com.dream.blog.service.ArticleService;
import com.dream.blog.service.TagService;
import com.dream.blog.service.WallpaperService;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.PageRecord;
import com.dream.common.utils.R;
import com.dream.common.utils.bean.BeanUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/blog")
public class ArticleController extends BaseController {

    // 文章service
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WallpaperService wallpaperService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/getArticleList")
    @ResponseBody
    public R getArticleList(@RequestBody ArticleQueryItem query) {
        PageHelper.startPage(query.getPageIndex(), query.getPageSize(), true);
        // 获取文章列表
        PageRecord<ArticleVoItem> pageRecord= articleService.getArticleList(query);
        return R.success().put("data", pageRecord);
    }
    @RequestMapping("/getArticlePage")
    @ResponseBody
    public R getArticlePage(@RequestBody ArticleQueryItem query) {
        PageHelper.startPage(query.getPageIndex(), query.getPageSize(), true);
        // 获取文章列表
        List<ArticleItem> articleList = articleService.getArticlePage(query);
        PageInfo<ArticleItem> pageInfo = new PageInfo<ArticleItem>(articleList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        PageRecord<ArticleItem> pageRecord = new PageRecord<ArticleItem>();
        pageRecord.setRows(articleList);
        pageRecord.setCurrentPage(query.getPageIndex());
        pageRecord.setCurrentPageSize(query.getPageSize());
        pageRecord.setTotalCount(total);
        pageRecord.setTotalPage(pages);
        return R.success().put("data", pageRecord);
    }
    @GetMapping("/getArticleDetail/{articleId}")
    @ResponseBody
    public R getArticleDetail(@PathVariable Integer articleId) {
        ArticleVoItem articleModel = new ArticleVoItem();
        if (articleId != null && articleId != 0) {
            ArticleItem articleItem = articleService.getById(articleId);
            List<Tag> tagList = tagService.getAll();
            BeanUtils.copyProperties(articleItem, articleModel);
            String articleTags = articleItem.getArticleTags();
            if (StringUtils.isNotEmpty(articleTags)) {
                List<String> aTagList = new ArrayList<>(Arrays.asList(articleTags.split(",")));
                List<Tag> tags = tagList.stream().filter(m -> aTagList.contains(m.getId().toString())).collect(Collectors.toList());
                articleModel.setArticleTags(tags);
            }
            List<ArticleFile> coverImages = articleService.getFileById(articleId);
            if(coverImages!=null&&coverImages.size()>0){
                articleModel.setCoverImageList(coverImages.stream().map(ArticleFile::getCoverImage).collect(Collectors.toList()));
            }else{
                WallpaperItem randomItem = wallpaperService.getRandomItem();
                if(randomItem!=null){
                    List<String> wallpager=new ArrayList<>();
                    wallpager.add(randomItem.getImgSrc());
                    articleModel.setCoverImageList(wallpager);
                }
            }

            ArticleDetail contentItem = articleService.getContentById(articleId);
            if (contentItem != null && StringUtils.isNotBlank(contentItem.getContent())) {
                articleModel.setContent(contentItem.getContent());
            }
        }
        return R.success().put("data", articleModel);
    }

    @RequestMapping("/deleteArticle/{articleId}")
    @ResponseBody
    public R deleteArticle(@PathVariable Integer articleId) {
        boolean result = false;
        try {
            result = articleService.deleteArticle(articleId);
            if (result) {
                return R.success("操作成功");
            }
        } catch (Exception e) {
            log.error("操作失败:{0}", e);
            log.error(e.getMessage());
        }
        return R.error("操作失败");
    }

    @RequestMapping("/saveArticle")
    @ResponseBody
    public R saveArticle(@RequestBody ArticleSubmitItem submitItem) {
        boolean result = false;
        try {
            Date time = DateUtils.getNowDate();
            List<String> tagIds=new ArrayList<>();
            //更新标签库
            if (StringUtils.isNotBlank(submitItem.getArticleTags())) {
                String[] tags = submitItem.getArticleTags().split(",");
                if (tags.length > 0) {
                    for (String tag : tags) {
                        //检查tag是存在
                        List<Tag> tagList = tagService.getTagByName(tag);
                        if (tagList != null && tagList.size() > 0) {
                            tagIds.add(tagList.get(0).getId().toString());
                            continue;
                        }
                        TagItem t = new TagItem();
                        t.setAlia(tag);
                        t.setColor("#EB6841");
                        t.setName(tag);
                        tagService.saveTag(t);
                        tagIds.add(t.getId()+"");
                    }
                }
            }
            String articleTagIds=String.join(",",tagIds);
            submitItem.setArticleTags(articleTagIds);
            if (submitItem.getId() == 0) {
                submitItem.setCreateTime(time);
                submitItem.setUpdateTime(time);
                submitItem.setPublishTime(DateUtils.dateTimeNow());
                submitItem.setAuthor("tc");
                articleService.saveArticle(submitItem);
                int id = Integer.parseInt(submitItem.getId().toString());
                ArticleDetail detail = new ArticleDetail();
                detail.setArticleId(id);
                detail.setContent(submitItem.getContent());
                detail.setCreateTime(time);
                detail.setUpdateTime(time);
                articleService.saveContent(detail);
                List<ArticleFile> files = new ArrayList<ArticleFile>();
                if (submitItem.getCoverImageList()!=null&&submitItem.getCoverImageList().length > 0) {
                    for (String url : submitItem.getCoverImageList()) {
                        ArticleFile img = new ArticleFile();
                        img.setArticleId(id);
                        img.setCoverImage(url);
                        img.setCreateTime(time);
                        img.setUpdateTime(time);
                        files.add(img);
                    }
                }
                if (files.size() > 0) {
                    articleService.saveImage(files);
                }
                result = true;
            } else {
                int id = submitItem.getId();
                submitItem.setUpdateTime(DateUtils.getNowDate());
                articleService.updateArticle(submitItem);
                //删除正文
                articleService.deleteContent(id);
                ArticleDetail detail = new ArticleDetail();
                detail.setArticleId(id);
                detail.setContent(submitItem.getContent());
                detail.setCreateTime(time);
                detail.setUpdateTime(time);
                //添加正文
                articleService.saveContent(detail);
                List<ArticleFile> files = new ArrayList<ArticleFile>();
                if (submitItem.getCoverImageList()!=null&&submitItem.getCoverImageList().length > 0) {
                    for (String url : submitItem.getCoverImageList()) {
                        ArticleFile img = new ArticleFile();
                        img.setArticleId(id);
                        img.setCoverImage(url);
                        img.setCreateTime(time);
                        img.setUpdateTime(time);
                        files.add(img);
                    }
                }
                //删除封面
                articleService.deleteImage(id);
                if (files.size() > 0) {
                    //添加封面
                    articleService.saveImage(files);
                }
                result = true;
            }

        } catch (Exception e) {
            log.error("操作失败:{0}", e);
            log.error(e.getMessage());
        }
        if (result) {
            return R.success("操作成功");
        }
        return R.error("操作失败");
    }
    @RequestMapping("/getArticleModel/{articleId}")
    public R getArticleModel(@PathVariable("articleId")int articleId) {
        Map<String,Object> articleModel=new HashMap<>();
        ArticleItem articleItem=articleService.getById(articleId);
        if(articleItem!=null){
            articleModel.put("title",articleItem.getTitle());
            articleModel.put("abstract",articleItem.getAbstractContent());
            //获取文章标签
            List<String> tags=new ArrayList<>();
            if(StringUtils.isEmpty(articleItem.getArticleTags())){
                List<String> tagIds=Arrays.asList(articleItem.getArticleTags().split(","));
                if(tagIds.size()>0){
                    List<Tag> tagItems=tagService.getTagByIds(tagIds);
                    if(tagItems!=null&&tagItems.size()>0){
                        tags=tagItems.stream().map(Tag::getAliasName).collect(Collectors.toList());
                    }
                }
            }
            articleModel.put("tag",tags);
            //获取细览信息
            ArticleDetail contentItem=articleService.getContentById(articleId);
            if(contentItem!=null&&StringUtils.isNotBlank(contentItem.getContent())){
                articleModel.put("content",contentItem.getContent());
            }

            articleModel.put("viewCount",articleItem.getViewCount());
            articleModel.put("title",articleItem.getTitle());
			/*//记录浏览量到redis,然后定时更新到数据库
			String key=RedisKey.ARTICLE_VIEWCOUNT_CODE+articleId;
			//找到redis中该篇文章的点赞数，如果不存在则向redis中添加一条
			Map<Object,Object> viewCountItem=redisUtil.hmget(RedisKey.ARTICLE_VIEWCOUNT_KEY);
			Integer viewCount=0;
			if(!viewCountItem.isEmpty()){
				if(viewCountItem.containsKey(key)){
					viewCount=(Integer)viewCountItem.get(key);
					redisUtil.hset(RedisKey.ARTICLE_VIEWCOUNT_KEY,key,viewCount+1);
				}else {
                    redisUtil.hset(RedisKey.ARTICLE_VIEWCOUNT_KEY,key,1);
                }
			}else{
				redisUtil.hset(RedisKey.ARTICLE_VIEWCOUNT_KEY,key,1);
			}*/
        }
        return R.success().put("data", articleModel);
    }
    @RequestMapping("/getRecommendArticleList")
    @ResponseBody
    public R getRecommendArticleList() {
        List<ArticleItem> articleList = articleService.getRecommendArticleList();
        return R.success().put("data", articleList);
    }
    @RequestMapping("/getTopReadArticleList")
    @ResponseBody
    public R getTopReadArticleList() {
        List<ArticleItem> articleList = articleService.getTopReadArticleList();
        return R.success().put("data", articleList);
    }
    @RequestMapping("/getRelevantArticle/{articleId}")
    @ResponseBody
    public R getRelevantArticle(@PathVariable  Integer articleId) {
        List<Map<String,Object>> articleList = articleService.getRelvantArticle(articleId);
        return R.success().put("data", articleList);
    }
}