import request from "@/utils/request";

class PartnerApi {
  getPartnerList() {
    return request({
      url: "/blog/getPartnerList",
      method: "get"
    });
  }
  savePartner(data) {
    return request({
      url: "/blog/partner/savePartner",
      method: "post",
      data: data
    });
  }
  deletePartner(partnerId) {
    return request({
      url: "/blog/partner/deletePartner/" + partnerId,
      method: "get"
    });
  }
}
export default new PartnerApi();
