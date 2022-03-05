import request from "@/utils/request";

class TagApi {
  // 查询文章分类
  getTagByIds(tagIds) {
    return request({
      url: "/blog/tag/getTagByIds/" + tagIds,
      method: "get"
    });
  }
  getTagList() {
    return request({
      url: "/blog/tag/getTagList",
      method: "get"
    });
  }
  saveTag(data) {
    return request({
      url: "/blog/tag/saveTag",
      method: "post",
      data: data
    });
  }
  deleteTag(tagId) {
    return request({
      url: "/blog/tag/deleteTag/" + tagId,
      method: "get"
    });
  }
}
export default new TagApi();
