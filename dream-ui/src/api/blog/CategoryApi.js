import request from "@/utils/request";

class CategoryApi {
  // 查询文章分类
  getCategoryData() {
    return request({
      url: "/blog/getCategoryData",
      method: "get"
    });
  }
  getCategoryTreeData(parentId) {
    return request({
      url: "/blog/getCategoryTreeData/" + parentId,
      method: "get"
    });
  }
  saveCategory(data) {
    return request({
      url: "/blog/category/saveCategory",
      method: "post",
      data: data
    });
  }
  deleteCategory(id) {
    return request({
      url: "/blog/category/deleteCategory/" + id,
      method: "get"
    });
  }
}
export default new CategoryApi();
