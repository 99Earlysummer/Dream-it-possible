import request from "@/utils/request";

class BannerApi {
  getList(query) {
    return request({
      url: "/blog/getBannerList",
      method: "post",
      data: query
    });
  }
  getDetail(resumeCode) {
    return request({
      url: "/blog/banner/getBannerDetail/" + resumeCode,
      method: "get"
    });
  }
  saveRuseme(data) {
    return request({
      url: "/blog/banner/saveBanner",
      method: "post",
      data: data
    });
  }
}
export default new BannerApi();
