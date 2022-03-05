<template>
  <footer>
    <div class="links grey-link">
      <div class="copyright grey-color p-h-sm d-none">
        © {{ copyrightrange }} Copyright by {{ company }} All rights reserved
      </div>
      <div class="icp grey-color">
        <a href="http://www.miaoleyan.com/" target="_blank"
          ><img alt="瞄了眼" src="@/assets/blog/image/logo.png"
        /></a>
        <a target="_blank" href="#"
          ><span class="icon icon-aq">{{ icpNo }}</span></a
        >
        <a><span ref="cnzzspan" :id="cnzz_stat_icon"></span></a>
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  name: 'BlogFooter',
  data() {
    return {
      company: '暖暖动听',
      icpNo: '京ICP备17043651号-2',
      copyrightrange: '2017-2021',
      cnzz_id: '1272821228'
    }
  },
  computed: {
    cnzz_stat_icon: function() {
      return 'cnzz_stat_icon_' + this.cnzz_id
    }
  },
  mounted() {
    this.initcnzz()
  },
  methods: {
    initcnzz() {
      // 创建cnzz统计js
      var cnzz_protocol =
        'https:' == document.location.protocol ? ' https://' : ' http://'
      const script = document.createElement('script')
      script.src =
        cnzz_protocol +
        's22.cnzz.com/z_stat.php?id=' +
        this.cnzz_id +
        '&show=pic'
      script.language = 'JavaScript'
      var cnzzSpan = this.$refs.cnzzspan
      cnzzSpan.appendChild(script)
    }
  }
}
</script>
<style scoped>
/*footer*/
footer {
  width: 100%;
  background: #333;
  color: #a5a4a4;
  text-align: center;
  clear: both;
}
footer a {
  color: #a5a4a4;
}
footer .links {
  font-size: 12px;
}
footer .icp span {
  line-height: 24px;
  white-space: nowrap;
}
footer img {
  display: inline;
  width: 50px;
}
</style>
