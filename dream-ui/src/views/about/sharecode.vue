<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-13 23:46:39
-->
<script>
import { ResumeApi } from "@/api/blog";
import { nanoid } from "nanoid";
import VueQr from 'vue-qr'
import {
  Form,
  Cell,
  CellGroup,
  Button,
  Divider,
  Toast,
} from "vant";
export default {
  components: {
    [Button.name]: Button,
    [Form.name]: Form,
    [Cell.name]: Cell,
    [CellGroup.name]: CellGroup,
    [Divider.name]: Divider,
    [Toast.name]: Toast,
    VueQr,
  },
  data() {
    return {
        shareId:"",
        editPath:"",
        sharePath:"",
        logo: 'https://pic3.zhimg.com/80/v2-b1c4069afdec39fdd6fb0888395fc02e_720w.jpg?source=1940ef5c'
    };
  },
  created() {
    if (this.$route.query.uuid) {
      this.uuid=this.$route.query.uuid;
      this.editPath=window.location.origin+"/resume?uuid="+this.uuid;
      this.createShareCode(this.uuid);
    }
  },
  methods: {
    createShareCode(uuid) {
      this.shareId = nanoid();
      this.sharePath=window.location.origin+"/shareme?gid="+this.shareId;
      ResumeApi.updateShareCode(uuid,this.shareId).then((response) => {
        if (response.code == 0) {
            Toast.success("保存好二维码方便修改和分享");
        }
      });
    },

  },
};
</script>
<template>
  <div>
    <ul>
      <li> <vue-qr ref="editQrcode" :text="editPath" :logoSrc="logo" qid="editQrId"></vue-qr></li>
      <li>扫描上方二维码进行编辑</li>
      <li><vue-qr ref="shareQrcode" :text="sharePath" :logoSrc="logo" qid="shareQrId"></vue-qr></li>
      <li>将上方二维码截图进行分享</li>
    </ul>

    <van-divider dashed></van-divider>

    <van-divider dashed></van-divider>
  </div>
</template>
<style scoped>
 ul{
   text-align: center !important;
    margin-top: 80px;
 }
</style>
