<template>
  <div class="s-main">
    <div class="white_circle"></div>
    <div class="fla fla_one">
      <div class="squ squ_one"></div>
      <div class="tri tri_one"></div>
    </div>
    <div class="fla fla_two">
      <div class="squ squ_two"></div>
      <div class="tri tri_two"></div>
    </div>
    <div class="fla fla_three">
      <div class="squ squ_three"></div>
      <div class="tri tri_three"></div>
    </div>
    <div class="fla fla_four">
      <div class="squ squ_four"></div>
      <div class="tri tri_four"></div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'SheroCat',
  data() {
    return {

    }
  },
  created() {},
  mounted() {},
  methods: {}
}
</script>
<style >
.s-main{
  width: 804px;
  height: 804px;
  animation: roll 3000ms linear infinite;
}
@keyframes roll{
  0%{transform: rotate(0deg);}
  100%{transform: rotate(-360deg);}
}
.white_circle{
  z-index: 99;
  position: absolute;
  width: 50px;
  height: 50px;
  border-radius: 50px;
  background-color: white;
  margin: 377px 0 0 377px;
}
.fla{
  position: absolute;
  width: 402px;
  height: 180px;
}
.fla_one{
  transform: rotate(270deg);
  margin:108px 0px 0px 121px;
}
.fla_two{
  transform: rotate(180deg);
  margin-top: 389px;
}
.fla_three{
  transform: rotate(90deg);
  margin: 511px 0 0 280px;
}
.fla_four{
  transform: rotate(360deg);
  margin: 230px 0 0 402px;
}
.tri{
  width: 0;
  height: 0;
  border-width: 142px;
  border-style: solid;
  border-top-left-radius: 109px 122px;
  position: absolute;
  transform: rotate(45deg);
  top: 28px;
  left: 56px;
}
.tri_one {
  border-color: #F1C40F transparent transparent #F1C40F;
}
.tri_two {
  border-color: #EA4C3C transparent transparent #EA4C3C;
}
.tri_three {
  border-color: #2ECC71 transparent transparent #2ECC71;
}
.tri_four {
  border-color: #1ABC9C transparent transparent #1ABC9C;
}
.squ{
  position: absolute;
  width: 271px;
  height: 170px;
  border-top-right-radius: 98px 68px;
}
.squ_one{
  background-color: #F39C12;
}
.squ_two{
  background-color: #C0392B;
}
.squ_three{
  background-color: #27AE60;
}
.squ_four{
  background-color: #16A085;
}
</style>
