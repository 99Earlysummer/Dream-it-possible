<template>
  <a target="_blank" href="https://github.com/MiracleTanC?tab=repositories">
    <img
      class="github"
      src="https://s3.amazonaws.com/github/ribbons/forkme_right_red_aa0000.png"
      alt="Fork me on GitHub"
    />
  </a>
</template>
<script>
export default {
  name: 'GitHubFlag',
  data() {
    return {
      offset: 300
    }
  },
  mounted() {},
  methods: {}
}
</script>
<style scoped>
.github {
  position: absolute;
  top: 56px;
  right: 0;
  border: 0;
  z-index: 999;
}
</style>
