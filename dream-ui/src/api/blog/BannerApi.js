import request from "@/utils/request";

class BannerApi {
  getBannerList(query) {
    return request({
      url: "/blog/getBannerList",
      method: "post",
      data: query
    });
  }
  getDetailData(bannerId) {
    return request({
      url: "/blog/banner/getBannerDetail/" + bannerId,
      method: "get"
    });
  }
  deleteBanner(bannerId) {
    return request({
      url: "/blog/banner/deleteBanner/" + bannerId,
      method: "get"
    });
  }
  saveBanner(data) {
    return request({
      url: "/blog/banner/saveBanner",
      method: "post",
      data: data
    });
  }
}
export default new BannerApi();
