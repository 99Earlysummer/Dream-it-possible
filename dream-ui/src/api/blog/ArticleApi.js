import request from "@/utils/request";

class ArticleApi {
  // 查询文章列表
  listData(query) {
    return request({
      url: "/blog/getArticleList",
      method: "post",
      data: query
    });
  }
  getArticlePage(query) {
    return request({
      url: "/blog/getArticlePage",
      method: "post",
      data: query
    });
  }
  // 查询文章明细
  getDetailData(articleId) {
    return request({
      url: "/blog/getArticleDetail/" + articleId,
      method: "get"
    });
  }
  // 前台首页查询文章明细,标签，记录浏览量
  getArticleModel(articleId) {
    return request({
      url: "/blog/getArticleModel/" + articleId,
      method: "get"
    });
  }
  //点击排行
  getTopReadArticleList() {
    return request({
      url: "/blog/getTopReadArticleList",
      method: "get"
    });
  }
  //热门推荐
  getRecommendArticleList() {
    return request({
      url: "/blog/getRecommendArticleList",
      method: "get"
    });
  }
  //相关推荐
  getRelevantArticle(articleId) {
    return request({
      url: "/blog/getRelevantArticle/" + articleId,
      method: "get"
    });
  }
  // 删除文章
  deleteData(articleId) {
    return request({
      url: "/blog/deleteArticle/" + articleId,
      method: "get"
    });
  }
  //保存文章
  saveArticle(data) {
    return request({
      url: "/blog/saveArticle",
      method: "post",
      data: data
    });
  }
}
export default new ArticleApi();
