import request from "@/utils/request";

class ResumeApi {
  getList(query) {
    return request({
      url: "/resume/getList",
      method: "post",
      data: query
    });
  }
  getDetail(resumeCode) {
    return request({
      url: "/resume/getDetail?uuid=" + resumeCode,
      method: "get"
    });
  }
  getShareData(shareCode) {
    return request({
      url: "/resume/getShareData?gid=" + resumeCode,
      method: "get"
    });
  }
  saveResume(data) {
    return request({
      url: "/resume/saveResume",
      method: "post",
      data: data
    });
  }
}
export default new ResumeApi();
