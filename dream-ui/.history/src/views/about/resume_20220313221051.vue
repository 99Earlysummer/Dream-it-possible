<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-13 22:09:54
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
  DatetimePicker,Toast
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
    [Toast .name]: Toast ,
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
      oldImgs:[],
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
        regionVal: "411521",
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
    } else {
      this.uuid = nanoid();
    }
  },
  methods: {
    dateFormat(data) {
      let delayTime = new Date(data).toJSON();
      let newdate = new Date(+new Date(delayTime) + 8 * 3600 * 1000)
        .toISOString()
        .replace(/T/g, " ")
        .replace(/\.[\d]{3}Z/, "");
      return newdate;
    },
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
          this.form.sex = this.form.sex + "";
          if (response.data.photos) {
            let ps = response.data.photos.split(",");
            let fs = ps.map((item) => ({ url: item, isImage: true }));
            this.fileList = fs;
          }
          this.form.xingZhuo = this.birthToConstellation(this.form.birth);
          this.form.shengXiao = this.birthToShengxiao(this.form.birth);
        }
      });
    },
    async onSubmit() {
      let photos = await this.onPhotosSubmit();
      if(oldImgs&&oldImgs.length>0){

      }
      this.form.photos = photos;
      let data = this.form;
      ResumeApi.saveResume(data).then((response) => {
        if (response.code == 0) {
          Toast.success(response.msg);
        }
      });
    },
    onConfirmRegion(data) {
      let province = data[0].name;
      let city = data[1].name;
      let contry = data[2].name;
      this.form.region = province + " " + city + " " + contry;
      this.form.regionItem = data;
      this.form.regionVal = data[2].code;
      this.showArea = false;
    },
    onConfirmBirth(data) {
      let dateStr = new Date(data).toJSON().split("T")[0];
      this.form.birth = dateStr;
      this.form.xingZhuo = this.birthToConstellation(this.form.birth);
      this.form.shengXiao = this.birthToShengxiao(this.form.birth);
      this.showCalendar = false;
    },
    onPhotosSubmit() {
      let ph = [];
      debugger
      if (!this.fileList || this.fileList.length == 0) {
        return "";
      }
      let arr=[];
      let formData = new FormData();
      for (let i = 0; i < this.fileList.length; i++) {
        if(this.fileList[i].file){
          formData.append("file", this.fileList[i].file);
        }else{
          arr.push( this.fileList[i].url)
        }
      }
      this.oldImgs = arr.join(",");
      return axios({
        url: this.imgApiUrl,
        method: "post",
        data: formData,
        headers: {
          Authorization: "Bearer " + getToken(),
          "Content-Type": "multipart/form-data",
        },
      }).then((res) => {
        // 上传完成需要更新 photos
        let imgs = res.data.result;
        if (imgs) {
          ph = imgs.map((item) => item.url);
        }
        return ph;
      });
    },
    beforeDelete(res) {
      this.fileList.splice(this.fileList.indexOf(res), 1);
    },
    afterRead(res) {},
  },
};
</script>
<template>
  <div>
    <van-form>
      <van-cell-group title="基本信息">
        <van-field
          v-model="form.name"
          name="姓名"
          label="姓名"
          placeholder="姓名"
          :rules="[{ required: true, message: '请填写姓名' }]"
        />

        <van-field name="radio" label="性别">
          <template #input>
            <van-radio-group v-model="form.sex" direction="horizontal">
              <van-radio name="0">男</van-radio>
              <van-radio name="1">女</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field
          label="生日"
          v-model="form.birth"
          placeholder="点击选择生日"
          @click="showCalendar = true"
        />
        <van-datetime-picker
          v-show="showCalendar"
          type="date"
          :value="currentDate"
          :min-date="minDate"
          :max-date="maxDate"
          @confirm="onConfirmBirth"
          @cancel="showCalendar = false"
        />
        <!-- <van-calendar :min-date="minDate" :max-date="maxDate"  @confirm="onConfirmBirth" /> -->
        <van-cell title="生肖" :value="form.shengXiao" />
        <van-cell title="星座" :value="form.xingZhuo" />
        <van-field
          v-model="form.phone"
          label="手机"
          placeholder="请输入手机号"
        />
        <van-field v-model="form.height" label="身高" placeholder="身高cm" />
        <van-field v-model="form.weight" label="体重" placeholder="体重kg" />
        <van-field
          v-model="form.region"
          is-link
          readonly
          name="area"
          label="籍贯"
          placeholder="点击选择省市区"
          @click="showArea = true"
        />
        <van-popup v-model:show="showArea" position="bottom">
          <van-area
            :value="form.regionVal"
            :area-list="regionList"
            @confirm="onConfirmRegion"
            @cancel="showArea = false"
          />
        </van-popup>
        <van-field name="uploader" label="照片">
          <template #input>
            <van-uploader
              :preview-size="60"
              :multiple="true"
              :max-count="9"
              :after-read="afterRead"
              :before-delete="beforeDelete"
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
