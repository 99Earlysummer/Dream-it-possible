<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-03-12 16:53:26
 * @LastEditors: your name
 * @LastEditTime: 2022-03-13 12:40:42
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
      showCalendar: false,
      showArea: false,
      regionList: areaList,
      minDate: new Date(1900, 0, 1),
      maxDate: new Date(),
      currentDate: new Date(),
      form: {
        uuid:"",
        name: "",
        sex: "1",
        birth: "",
        shengXiao: "",
        xingZhuo: "",
        height: "",
        weight: "",
        region: "",
        regionItem:[],
        school: "",
        company: "",
        phone: "",
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
   created() {
    this.uuid = this.$route.query.uuid
    this.initDetail(this.uuid)
    this.loadCategoryOptions()
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
    initDetail(uuid){
       ResumeApi.getDetail(uuid).then((response) => {
         if (response.code == 0) {
            this.form=response.data;
            let regionItems=response.data.regionItem;
             this.form.region=regionItems[0].name+" "+regionItems[1].name+" "+regionItems[2].name;
             this.form.region=regionItems[0].name+" "+regionItems[1].name+" "+regionItems[2].name;

         }
       });
    },
    onSubmit() {
      let data = this.form;
      debugger;
      console.log(data);
      // ResumeApi.saveResume(data).then((response) => {
      //   if (response.code == 0) {
      //    Toast.success(response.msg);
      //   }
      // });
    },
    onRegionConfirm(data) {
      console.log(data)
      let province=data[0].name;
      let city=data[1].name;
      let contry=data[2].name;
      this.form.region=province+" "+city+" "+contry;
      this.form.regionItem=data;
      this.showArea=false;
    },
    onConfirmBirth(data) {
      let dateStr = new Date(data).toJSON().split("T")[0];
      this.form.birth = dateStr;
      this.form.xingZhuo = this.birthToConstellation(this.form.birth);
      this.form.shengXiao = this.birthToShengxiao(this.form.birth);
      this.showCalendar = false;
    },
    afterRead(res) {
      console.log(res);
    },
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
        <van-datetime-picker v-show="showCalendar"
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
          placeholder="点击选择省市区" value="110118"
          @click="showArea = true"
        />
        <van-popup v-model:show="showArea" position="bottom">
          <van-area
            :area-list="regionList"
            @confirm="onRegionConfirm"
            @cancel="showArea = false"
          />
        </van-popup>
        <van-field name="uploader" label="照片">
          <template #input>
            <van-uploader
              :preview-size="60"
              multiple
              :max-count="9"
              :after-read="afterRead"
              v-model="form.photos"
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
