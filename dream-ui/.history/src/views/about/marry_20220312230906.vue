<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-12 23:08:34
-->
<script>
import { areaList } from "@vant/area-data";
import { ResumeApi } from "@/api/blog";
import {
  Form,
  Field,
  Cell,
  CellGroup,
  Button,
  RadioGroup,
  Radio,
  Calendar,
  Area,
  Popup,
  Uploader,
  Divider
} from "vant";
export default {
  components: {
    [Button.name]: Button,
    [Form.name]: Form,
    [Field.name]: Field,
    [Cell.name]: Cell,
    [CellGroup.name]: CellGroup,
    [RadioGroup.name]: RadioGroup,
    [Radio.name]: Radio,
    [Calendar.name]: Calendar,
    [Area.name]: Area,
    [Popup.name]: Popup,
    [Uploader.name]: Uploader,
    [Divider.name]: Divider,
    areaList,
  },
  data() {
    return {
      showCalendar: false,
      showArea: false,
      form: {
        name: "",
        sex: "1",
        birth: "",
        shengXiao: "",
        xingZhuo: "",
        height: "",
        weight: "",
        region: "",
        school: "",
        company: "",
        phone: "",
        regionList: areaList,
        description: "",
        wanted: "",
        headImage: "",
        photos: [
          { url: "https://img.yzcdn.cn/vant/leaf.jpg" },
          // Uploader 根据文件后缀来判断是否为图片文件
          // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
          { url: "https://cloud-image", isImage: true },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      let data = this.form;
      debugger;
      // ResumeApi.saveResume(data).then((response) => {
      //   if (response.code == 0) {
      //    Toast.success(response.msg);
      //   }
      // });
    },
    onConfirm() {},
    afterRead(res) {
      console.log(res);
    },
  },
};
</script>
<template>
  <div
</template>
<style scoped>
.mr-20 {
  margin-right: 20px;
}
</style>
