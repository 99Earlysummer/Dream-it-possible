<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-13 22:43:24
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

} from "vant";
export default {
  components: {
    [Button.name]: Button,
    [Form.name]: Form,
    [Cell.name]: Cell,
    [CellGroup.name]: CellGroup,
    [Divider.name]: Divider,
    VueQr,
  },
  data() {
    return {
        shareId:"",
        editPath:""
        editPath:""
    };
  },
  created() {
    if (this.$route.query.uuid) {
      this.editPath=window.location.origin+"/resume?uuid="+this.$route.query.uuid;
      this.initDetail(this.uuid);
    }
  },
  methods: {
    createShareCode(uuid) {
      this.shareId = nanoid();
      this.editPath=window.location.origin+"/resume?uuid="+this.$route.query.uuid;
      ResumeApi.getDetail(uuid).then((response) => {
        if (response.code == 0) {
          this.form = response.data;
          let regionItems = response.data.regionItem;
          this.form.region =
            regionItems[0].name +
            " " +
            regionItems[1].name +
            " " +
            regionItems[2].name;
          this.form.regionVal = regionItems[2].code;
          this.fileList=response.data.photos.split(",");
        }
      });
    },

  },
};
</script>
<template>
  <div>
    <vue-qr ref="Qrcode" :text="qrCode.text" :logoSrc="qrCode.logo" qid="testQrId"></vue-qr>
    <van-divider dashed></van-divider>
  </div>
</template>
<style scoped>
 .custom-indicator {
    position: absolute;
    right: 5px;
    bottom: 5px;
    padding: 2px 5px;
    font-size: 12px;
    background: rgba(0, 0, 0, 0.1);
  }
</style>
