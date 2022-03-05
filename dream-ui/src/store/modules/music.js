import { MusicApi } from "@/api/blog";

const music = {
  state: {
    musicData: []
  },

  mutations: {
    SET_MUSICDATA: (state, musicData) => {
      state.musicData = musicData;
    }
  },

  actions: {
    // 获取音乐列表
    GetMusicData({ commit }) {
      return new Promise((resolve, reject) => {
        MusicApi.getTopMusicList({})
          .then(res => {
            if (res.code==0&&res.data.length>0 ) {
              const musicList = formatMusicData(res.data);
              commit("SET_MUSICDATA", musicList);
            } else {
              let defaultData = [
                {
                  name: "暖暖",
                  artist: "梁静茹",
                  url:
                    "http://file.miaoleyan.com/nndt/SU8Wxyi9s7VLtJlNsQl3a3Bw0Gm53Qm0",
                  cover:
                    "https://tvax2.sinaimg.cn/crop.0.0.512.512.180/67ab17f5ly8giqfx4tro7j20e80e8dfz.jpg?KID=imgbed,tva&Expires=1606910591&ssig=BloRN1gnw0"
                }
              ];
              commit("SET_MUSICDATA", defaultData);
            }
            resolve();
          })
          .catch(error => {
            reject(error);
          });
      });
    }
  }
};
function formatMusicData(dataList) {
  var arr = [];
  for (var item of dataList) {
    var currentModel = {
      url: item.url,
      name: item.title,
      artist: item.singer,
      cover: item.coverUrl
    };
    arr.push(currentModel);
  }
  return arr;
}
export default music;
