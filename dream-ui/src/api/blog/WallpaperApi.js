import request from "@/utils/request";

class WallpaperApi {
  getWallpaper(query) {
    return request({
      url: "/blog/getWallpaperList",
      method: "post",
      data: query
    });
  }
  getDetailData(wallpaperId) {
    return request({
      url: "/blog/wallpaper/getWallpaperDetail/" + wallpaperId,
      method: "get"
    });
  }
  deleteWallpaper(wallpaperId) {
    return request({
      url: "/blog/wallpaper/deleteWallpaper/" + wallpaperId,
      method: "get"
    });
  }
  saveWallpaper(data) {
    return request({
      url: "/blog/wallpaper/saveWallpaper",
      method: "post",
      data: data
    });
  }
}
export default new WallpaperApi();
