import request from "@/utils/request";

class BannerApi {
  getBannerList(query) {
    return request({
      url: "/blog/getBannerList",
      method: "post",
      data: query
    });
  }
  getDetail(resumeCode) {
    return request({
      url: "/blog/banner/getBannerDetail/" + rusemeCode,
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
