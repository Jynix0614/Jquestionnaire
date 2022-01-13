<template>
  <el-main height="">
    <!-- Main content  数据域   表单ID   宽度  表单展示方向  表单大小-->
    <el-form
      :model="parms"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="normal"
    >
      <el-form-item label="问卷标题">
        <el-input v-model="parms.title"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search">搜索</el-button>
        <el-button style="color: #ff7670" icon="el-icon-close">重置</el-button>
        <el-button type="primary" @click="addBtn" icon="el-icon-plus"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 新增弹框 -->
    <sys-dialog
      :title="addDialog.title"
      :width="addDialog.width"
      :height="addDialog.height"
      :visible="addDialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <template slot="content">
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="80px"
          :inline="true"
          size="normal"
        >
          <el-form-item prop="phone" label="电话">
            <el-input v-model="addModel.phone"></el-input>
          </el-form-item>
          <el-form-item prop="email" label="邮箱">
            <el-input v-model="addModel.email"></el-input>
          </el-form-item>
          <el-form-item prop="username" label="账户">
            <el-input v-model="addModel.username"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="密码">
            <el-input v-model="addModel.password"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>

<script>
import SysDialog from "@/components/system/SysDialog.vue";
export default {
  //注册要引用的组件
  components: { SysDialog },
  //页面展示数据定义
  data() {
    return {
      //弹框属性
      addDialog: {
        title: "",
        width: 650,
        height: 250,
        visible: false,
      },
      parms: {
        title: "",
      },
      addModel: {
          phone:"",
          email:"",
          username:'',
          password:""
      },
      rules: {
          phone:[{
              trigger:'change',
              message:'请填写电话',
              required:true
          }],
          email:[{
              trigger:'change',
              message:'请填写邮箱',
              required:true
          }],
          username:[{
              trigger:'change',
              message:'请填写账户',
              required:true
          }],
          password:[{
              trigger:'change',
              message:'请填写密码',
              required:true
          }]
      },
    };
  },
  methods: {
    addBtn() {
      this.addDialog.title = "新增用户";
      this.addDialog.visible = true;
    },
    onConfirm() {
        this.$refs.addForm.validate(valid=>{
            if(valid){
                this.addDialog.visible = false;
            }
        })
    },
    onClose() {
      this.addDialog.visible = false;
    },
  },
};
</script>

<style lang="scss" scoped>
</style>