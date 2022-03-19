<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-13 15:37:10
-->
<script>
import axios from "axios";
import { nanoid } from "nanoid";
import { areaList } from "@vant/area-data";
import { ResumeApi } from "@/api/blog";
import { getToken } from "@/utils/auth";
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
  Divider,
  DatetimePicker,
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
    [DatetimePicker.name]: DatetimePicker,
    areaList,
  },
  data() {
    return {
      imgApiUrl: process.env.VUE_APP_BASE_API + "/qiNiu/upload",
      showCalendar: false,
      showArea: false,
      regionList: areaList,
      minDate: new Date(1900, 0, 1),
      maxDate: new Date(),
      currentDate: new Date(),
      fileList: [],
      form: {
        uuid: "",
        name: "",
        sex: "1",
        birth: "",
        shengXiao: "",
        xingZhuo: "",
        height: "",
        weight: "",
        region: "",
        regionVal: "",
        regionItem: [],
        school: "",
        company: "",
        phone: "",
        description: "",
        wanted: "",
        headImage: "",
        photos: "",
      },
    };
  },
  created() {
    if (this.$route.query.uuid) {
      this.uuid = this.$route.query.uuid;
      this.initDetail(this.uuid);
    }
  },
  methods: {
    initDetail(uuid) {
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
    <van-form>
      <van-cell-group title="基本信息">
        <van-cell title="姓名" :value="form.name" />
        <van-cell title="性别" :value="form.sex==0?'男':'女'" />
        <van-cell title="生日" :value="form.birth" />
        <van-cell title="生肖" :value="form.shengXiao" />
        <van-cell title="星座" :value="form.xingZhuo" />
        <van-cell title="手机" :value="form.phone" />
        <van-cell title="身高(cm)" :value="form.height" />
        <van-cell title="体重(kg)" :value="form.weight" />
        <van-cell title="籍贯" :value="form.school" />
        <van-cell title="学校" :value="form.regionVal" />
        <van-cell title="籍贯" :value="form.regionVal" />

        <van-field name="uploader" label="照片">
          <template #input>
            <van-uploader
              :preview-size="60"
              :multiple="true"
              :max-count="9"
              :after-read="afterRead"
              v-model="fileList"
            />
          </template>
        </van-field>

        <van-field
          v-model="form.school"
          label="学校"
          placeholder="请输入毕业院校"
        />
        <van-field
          v-model="form.company"
          label="单位"
          placeholder="请输入就职单位"
        />
        <van-field
          v-model="form.description"
          rows="3"
          autosize
          label="自我介绍"
          type="textarea"
          maxlength="500"
          placeholder="展现优秀的你！"
          show-word-limit
        />
        <van-field
          v-model="form.wanted"
          rows="3"
          autosize
          label="对Ta的期待"
          type="textarea"
          maxlength="500"
          placeholder="喜欢和合适总能撞个满怀"
          show-word-limit
        />
      </van-cell-group>
    </van-form>
    <van-button block class="mb-10" type="primary" @click="onSubmit"
      >保存</van-button
    >
    <van-button block plain class="mb-10" type="primary">预览</van-button>
    <van-divider dashed></van-divider>
  </div>
</template>
<style scoped>
.mb-10 {
  margin-bottom: 10px;
}
</style>
