<template>
  <div class="dashboard-container">
    <!-- <div class="dashboard-text">name: {{ name }}</div> -->
    <h3 class="dashboard-title">标题</h3>
    <div class="content">
    <p>文本文本文本文本文本文本文本文本文本文本文本文本文本文本</p>
    <img :src="logo" class="logo-sty"/>
    <div>
      <span>链接：</span><a href="http://www.w3school.com.cn">W3School</a>
    </div>
    </div>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data(){
    return{
      logo:require('@/assets/logo-min.png')
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-title{
    text-align: center;
  }
  &-container {
    margin: 10px 30px;
    padding:20px;
    .logo-sty{
    width:50px;
    height:50px;
    }
    .content{
       padding:20px;
      background:#eee;
    }
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
 
}
</style>
