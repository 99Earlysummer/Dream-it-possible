<template>
  <div>
    <div
      ref="rotateIcon"
      @click="showMusicPanel"
      :class="[musicPlayState ? 'rotate' : '', 'audio_icon']"
    >
      <svg
        t="1580201294240"
        class="icon"
        viewBox="0 0 1024 1024"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
        p-id="2473"
        width="25"
        height="25"
      >
        <path
          d="M839.8 64c-70.71 0.78-169.12 46.32-236.88 69.2-46.6 15.73-92.32 38.38-140.56 48.83-41.74 9-82.08 5-123.85 10.9-15 2.13-24.41 19.25-25 32.85-3 68.09 14.54 140.18 26.67 207.8-2.68 49.21-6.9 98.4-14.56 148-5.5 35.65-11.13 70.83-4.43 103.84a381.35 381.35 0 0 0-62.92 11.39c-57.61 15.4-117.48 38.31-140.19 98.42-6.42 17-8.07 32.75-6.44 47.32 2.9 53.95 43.24 104.38 108.7 114.9 105.45 16.95 259.74-52.53 251-175.55-3.38-47.39-27.07-73.67-59.35-86.8 9.16-46.45-2.33-115-4.16-139.08-3.87-51-13.07-104.6-20.15-158.29 146.72-2.09 286.82-89.37 430.54-106.87a44 44 0 0 0 10.11-2.53c-8.75 111.27-7.82 233.35-31.33 341.9a160.55 160.55 0 0 1-16.33-1.81c-14.2-2.2-27.69-1.59-42-0.74-36.26 2.16-74.24 11.55-105.32 30.69-112.76 69.42-75.56 228.92 55.54 241.35s220.56-105.33 165.8-211.06c21.71-27.3 16.61-107.71 19-128.68 10-87.75 16-175.91 23.39-263.92C902.63 229.7 952.84 62.76 839.8 64zM701.17 834.71q-68.95-137.57 37.44-134.85a179.65 179.65 0 0 1 50 2.7c8.58 13.74 16 28.09 21.33 43.65q-23.52 86.18-108.77 88.5z m-300.66-67.25c7.3 0.55 12.1-3.06 14.94-8 22.54 38.86-9.6 94.7-43.3 115.37-22.86 14-54 25.82-84.36 32.12 7.32-17.88 13.4-36.18 17.23-55.18 0.78-3.88-5.15-5.56-6-1.65a262.52 262.52 0 0 1-19.73 58.48 199.25 199.25 0 0 1-35.72 3.26 109.82 109.82 0 0 1-21.86-2.67c0.24-46 33.64-90.91 52.21-130.84 2.53-5.43-5.55-10.2-8.09-4.73-19.42 41.77-52.93 84.92-56.31 132.32-0.64-0.21-1.35-0.3-2-0.53 3.81-9.12 2.2-20-9.6-26.5-1.46-0.81-2.6-1.68-4-2.51 8-38.4 23.13-77.56 47.27-107.64 3.89-4.84-2.6-11.76-7-7-25.15 27.39-44.37 67.49-52.4 106-45.32-37.88-7.9-89.13 45.1-109.74a375 375 0 0 1 38.83-12.42 10.07 10.07 0 0 0 6.17 2.59c17.54 1.15 34.08 6.33 51 10.42a2.86 2.86 0 0 0 2.58 3.72l9.64 0.44a4.69 4.69 0 0 0 3-0.93c20.65 3.57 41.62 4.04 62.4 5.62z m435.88-540.29c-6.91-4.54-16-6.66-27.42-4.33-146.11 30-277.25 119.28-425 145.17-4.18-37.26-6.65-74.37-4.8-110.52 34-1.4 67.12-0.32 101.54-9 59.74-15.15 116.45-41.42 175.19-59.85 31.27-9.81 147.7-63.62 175.59-51.51 14.64 6.34 10.39 51.75 4.9 90.04z"
          fill="#ffffff"
          p-id="2474"
        ></path>
      </svg>
    </div>
    <div id="aplayer"></div>
    <div id="musicnote">
      <div id="pdyf1" class="note">
        <i class="fa fa-music" aria-hidden="true"></i>
      </div>
      <div id="pdyf2" class="note">
        <i class="fa fa-music" aria-hidden="true"></i>
      </div>
      <div id="pdyf3" class="note">
        <i class="fa fa-music" aria-hidden="true"></i>
      </div>
    </div>
  </div>
</template>
<script>
import '@/assets/blog/music.css'
import 'aplayer/dist/APlayer.min.css';
import APlayer from 'aplayer';
import { MusicApi } from '@/api/blog'

export default {
  name: 'MusicPlayer',
  components: {
  },
  props: {},
  data() {
    return {
      musicData:[],
      musicPanelShowState: false,
      musicPlayState:true,
      errorImg: 'this.src="/images/music/a1.png"',
      isload: false,
      aplayerMo:{}
    }
  },
  created() {

  },
  mounted() {
    //初始化音乐列表
    this.getTopMusicList()
    console.log('初始化音乐完成')
    if(!this.aplayerMo.paused){
      this.musicPlayState=true;
    }else{
      this.musicPlayState=false;
    }
  },
  watch: {},
  computed: {
   /* musicData() {
      return this.$store.getters.musicData
    }*/
  },
  methods: {
    play() {
      this.aplayerMo.play()
    },
    pause() {
      this.aplayerMo.pause()
    },
    showMusicPanel() {
      this.aplayerMo.toggle()
      if(!this.aplayerMo.paused){
        this.musicPlayState=true;
      }else{
        this.musicPlayState=false;
      }

    },
    listenerFunction() {
      var _this = this
      document.addEventListener('scroll', function(e) {
        this.aplayerMo.play()
      })
      var startx;
      var starty;
      //手指接触屏幕
      document.addEventListener(
        'touchstart',
        function(e) {
          startx = e.touches[0].pageX
          starty = e.touches[0].pageY
        },
        false
      )
      //手指离开屏幕
      document.addEventListener(
        'touchend',
        function(e) {
          var endx, endy
          endx = e.changedTouches[0].pageX
          endy = e.changedTouches[0].pageY
          var direction = getDirection(startx, starty, endx, endy)
          if (direction && direction > -1 && _this.$refs.player.paused) {
            _this.aplayerMo.play()
            _this.isload = true
          }
        },
        false
      )
      //微信浏览器
      document.addEventListener(
        'WeixinJSBridgeReady',
        function(e) {
          _this.aplayerMo.play()
          _this.isload = true
        },
        false
      )
    },
    getTopMusicList(){
      var _this=this;
      MusicApi.getTopMusicList({})
        .then(res => {
          if (res.code==0&&res.data.length>0 ) {
            _this.musicData = _this.formatMusicData(res.data);
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
            _this.musicData =defaultData;
          }
          this.aplayerMo=new APlayer({
            container: document.getElementById('aplayer'),
            //mini: false,
            autoplay: true,
            fixed: false,
            theme: '#FADFA3',
            loop: 'all',
            order: 'list',
            preload: 'auto',
            volume: 0.7,
            mutex: true,
            listFolded: false,
            listMaxHeight: 90,
            lrcType: 3,
            audio: _this.musicData
          })
        })
        .catch(error => {
        });
    },
    formatMusicData(dataList) {
      var arr = []
      for (var item of dataList) {
        var currentModel = {
          url: item.url,
          name: item.title,
          artist: item.singer,
          cover: item.converUrl
        }
        arr.push(currentModel)
      }
      return arr
    }
  }
}
</script>
<style >

.aplayer-pic {
  transition: 0.5s;
  border-radius: 50%;
  border: 2px #ff8c00 solid;
  -webkit-animation: rotating 3.5s linear infinite;
  -moz-animation: rotating 3.5s linear infinite;
  -o-animation: rotating 3.5s linear infinite;
  animation: rotating 3.5s linear infinite;
}
.aplayer.aplayer-fixed .aplayer-body{margin-left: 6px;}

.aplayer .aplayer-miniswitcher .aplayer-icon path {
  fill: #ff8c00 !important;
}
.aplayer .aplayer-miniswitcher {
  background: rgba(0, 0, 0, 0.1);
}
.aplayer-narrow {
  -webkit-transform: translate3d(-77px, 0, 0);
  -webkit-transform: translate3d(-77px, 0, 0);
  -moz-transform: translate3d(-77px, 0, 0);
  -ms-transform: translate3d(-77px, 0, 0);
  transform: translate3d(-77px, 0, 0);
}
#musicnote {
  position: fixed;
  bottom: 20px;
  left: 0;
  right: 0;
  margin: 0;
  z-index: 999;
}
#musictouch * {
  box-sizing: content-box;
}
[class*=' fa-']:before,
[class^='fa-']:before {
  margin: unset !important;
}
#pdyf1 {
  animation: mymove 2s linear 0s infinite normal;
}
@keyframes mymove {
  0% {
    left: 15px;
    top: -15px;
    opacity: 0;
  }
  50% {
    opacity: 1;
    transform: rotate(9deg);
  }
  100% {
    left: 200px;
    top: -90px;
    opacity: 0;
  }
}
#pdyf2 {
  animation: mymove2 2s linear 0.4s infinite normal;
}
@keyframes mymove2 {
  0% {
    left: 15px;
    top: -15px;
    opacity: 0;
  }
  50% {
    opacity: 1;
    transform: rotate(-9deg);
  }
  100% {
    left: 200px;
    top: -80px;
    opacity: 0;
  }
}
#pdyf3 {
  animation: mymove3 2s linear 0.7s infinite normal;
}
@keyframes mymove3 {
  0% {
    left: 15px;
    top: -15px;
    opacity: 0;
  }
  50% {
    opacity: 1;
    transform: rotate(9deg);
  }
  100% {
    left: 200px;
    top: -70px;
    opacity: 0;
  }
}
.note {
  display: block;
  opacity: 0;
  width: 25px;
  height: 25px;
  position: absolute;
  background: unset;
  margin: unset;
  padding: unset;
  z-index: 99999999;
  color: red;
}
</style>
