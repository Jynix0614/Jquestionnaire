<template>
  <div class="dashboard-container">
    <!-- <div class="dashboard-text">name: {{ name }}</div> -->
    <h1 class="dashboard-title">开发文档</h1>
    <div class="content">
      <h2 class="dashboard-l2title">一、总体架构草图</h2>
      <div style="text-align: center; padding: 20px">
        <img :src="p1" class="p1" />
      </div>

      <h2 class="dashboard-l2title">二、需求分析草图</h2>
      <div style="text-align: center; padding: 20px">
        <img :src="p2" class="p2" />
      </div>

      <h2 class="dashboard-l2title">三、相关技术</h2>
      <div class="text-new" style="text-align: left; padding: 10px">
        <div>
          <span>后台框架：</span
          ><a class="myherf" href="https://spring.io/">SpringBoot 2.0</a
          ><span> + </span
          ><a class="myherf" href="https://baomidou.com/">Mybatis-plus</a>
        </div>
        <div>
          <span>前端框架：</span
          ><a
            class="myherf"
            href="https://panjiachen.github.io/vue-element-admin-site/zh/"
            >vue-element-admin</a
          ><span> + </span
          ><a class="myherf" href="https://element.eleme.cn/#/zh-CN"
            >Element UI插件</a
          >
        </div>
        <div>
          <span>小程序框架：</span
          ><a
            class="myherf"
            href="https://developers.weixin.qq.com/miniprogram/dev/framework/"
            >小程序开发文档</a
          ><span> + </span
          ><a class="myherf" href="https://mp.weixin.qq.com/">微信公众平台</a>
        </div>
        <div>
          <span>素材库：</span
          ><a class="myherf" href="https://www.iconfont.cn/"
            >阿里巴巴矢量图标库</a
          >
        </div>
      </div>

      <h2 class="dashboard-l2title">四、源码地址</h2>
      <div class="text-new">
        <span>Github：</span
        ><a class="myherf" href="https://github.com/Jynix0614"
          >肥肠大表哥的主页</a
        >
      </div>

      <h2 class="dashboard-l2title">五、小程序踩坑之个人经验总结</h2>
      <h2 class="dashboard-l2title" style="text-align: left; padding: 10px">
        1.快速开始？
        <div class="text-new">
          html + js + css + Vue.js + 官方文档<a
            class="myherf"
            href="https://developers.weixin.qq.com/miniprogram/dev/framework/structure.html"
            >【官方】快速上手之工程结构介绍</a
          >。
        </div>
      </h2>

      <h2 class="dashboard-l2title" style="text-align: left; padding: 10px">
        2.重要概念：生命周期
        <div>
          <span
            >了解生命周期就能灵活游走于各个页面之间，包括页面间数据同步、异步通信。</span
          ><a
            class="myherf"
            href="https://developers.weixin.qq.com/miniprogram/dev/framework/app-service/page-life-cycle.html"
            >【官方】生命周期介绍</a
          >
        </div>
      </h2>

      <h2 class="dashboard-l2title" style="text-align: left; padding: 10px">
        3.必经之路：用户openid获取
        <div>
          <span
            >只要你开发的是个web应用就一定逃不过这一步，openid就是每个用户登陆时的唯一标识，可以通俗理解为微信号。</span
          ><a
            class="myherf"
            href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html"
            >【官方】openid获取</a
          >
        </div>
      </h2>

      <h2 class="dashboard-l2title" style="text-align: left; padding: 10px">
        4.容易踩坑：页面间通信
        <div><span>页面跳转就会传参，但微信的传参有两种方式：</span></div>
        <div>
          <span
            >一种是直接赋值，如this.username=username,这种是不会双向绑定的，需要刷新才会更新值。</span
          >
        </div>
        <div><span>一种是调用set方法，这种是双向绑定的，无需刷新。</span></div>
      </h2>

      <h2 class="dashboard-l2title" style="text-align: left; padding: 10px">
        5.测试环境or体验环境or生产环境
        <div><span>生产环境使用的是HTTPS协议，故需要证书。</span></div>
        <div>
          <span>测试环境or体验环境在一个局域网内即可，但体验人数有限。</span>
        </div>
        <div>
          详见<a
            class="myherf"
            href="https://mp.weixin.qq.com/wxamp/home/guide?token=769684679&lang=zh_CN"
            >【官方】如何测试</a
          >
        </div>
        
      </h2>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
  data() {
    return {
      logo: require("@/assets/images/logo-min.png"),
      p1: require("@/assets/images/架构图.png"),
      p2: require("@/assets/images/需求.png"),
    };
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-title {
    text-align: center;
  }
  &-container {
    margin: 10px 30px;
    padding: 20px;
    .logo-sty {
      width: 50px;
      height: 50px;
    }
    .content {
      padding: 20px;
      background: #eee;
    }
    .p1 {
      width: 70%;
      height: 70%;
    }
    .p2 {
      width: 70%;
      height: 60%;
    }
    .text-new {
      font-size: 25px;
      line-height: 46px;
    }
    .myherf {
      font-size: 25px;
      line-height: 46px;
      color: rgb(0, 34, 228);
    }
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
  &-l2title {
    text-align: left;
  }
}
</style>
