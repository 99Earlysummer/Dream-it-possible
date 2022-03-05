import request from "@/utils/request";

class MusicApi {
  getMusicList(data) {
    return request({
      url: "/blog/getMusicList",
      method: "post",
      data: data
    });
  }
  getTopMusicList(){
    return request({
      url: "/blog/getTopMusicList",
      method: "get"
    });
  }
  getWyCloudPlayList(data) {
    return request({
      url: "/blog/getWyCloudPlayList",
      method: "get"
    });
  }

  getMusicDetail(musicId) {
    return request({
      url: "/blog/music/getMusicDetail/" + musicId,
      method: "get"
    });
  }
  saveMusic(data) {
    return request({
      url: "/blog/music/saveMusic",
      method: "post",
      data: data
    });
  }
  batchSaveMusic(data) {
    return request({
      url: "/blog/music/batchSaveMusic",
      method: "post",
      data: data
    });
  }
  deleteMusic(musicId) {
    return request({
      url: "/blog/music/deleteMusic/" + musicId,
      method: "get"
    });
  }
}
export default new MusicApi();
