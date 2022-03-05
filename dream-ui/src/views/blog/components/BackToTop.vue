<template>
  <div class="side-toolbar">
    <div class="github">
      <a
        target="_blank"
        href="https://github.com/MiracleTanC?tab=repositories"
        class="cd-github"
      ></a>
    </div>
    <div class="backtop">
      <a
        @click="backToTop"
        href="javascript:void(0)"
        :class="[scrollTop > offset ? 'cd-is-visible' : '', 'cd-top']"
      ></a>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BackToTop',
  data() {
    return {
      offset: 300,
      offset_opacity: 1200,
      scroll_top_duration: 700,
      scrollTop: 0
    }
  },
  mounted() {
    window.addEventListener('scroll', () => {
      var scrollTop = document.documentElement.scrollTop
      this.scrollTop = scrollTop
    })
  },
  methods: {
    backToTop() {
      let timer = setInterval(function() {
        var osTop =
          document.documentElement.scrollTop || document.body.scrollTop
        var ispeed = Math.floor(-osTop / 5)
        document.documentElement.scrollTop = document.body.scrollTop =
          osTop + ispeed
        if (osTop === 0) {
          clearInterval(timer)
        }
      }, 30)
    }
  }
}
</script>
<style scoped>
/*cd-top*/
.cd-top {
  display: inline-block;
  height: 40px;
  width: 40px;
  position: fixed;
  bottom: 40px;
  right: 0px;
  overflow: hidden;
  text-indent: 100%;
  white-space: nowrap;
  background: rgba(0, 0, 0, 0) url(~@/assets/blog/image/backtop.png) no-repeat center;
  visibility: hidden;
  opacity: 0;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  transition: all 0.3s;
}

.cd-top.cd-is-visible {
  visibility: visible;
  opacity: 1;
}
.github {
  position: fixed;
  right: 0;
  z-index: 999999;
}
.cd-github {
  display: inline-block;
  z-index: 99;
  height: 40px;
  width: 40px;
  position: fixed;
  top: 218px;
  right: 0px;
  overflow: hidden;
  text-indent: 100%;
  white-space: nowrap;
  background: rgba(0, 0, 0, 0) url(~@/assets/blog/image/github.png) no-repeat
    center;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  transition: all 0.3s;
}
</style>
