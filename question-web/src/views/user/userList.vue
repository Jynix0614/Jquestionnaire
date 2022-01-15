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
      <el-form-item label="账户">
        <el-input v-model="parms.username"></el-input>
      </el-form-item>
      <el-form-item >
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button style="color: #ff7670" icon="el-icon-close" @click="resetBtn"
          >重置</el-button
        >
        <el-button type="primary" @click="addBtn" icon="el-icon-plus"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 用户表格    表格数据-->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column prop="username" label="账户"> </el-table-column>
      <el-table-column prop="phone" label="电话"> </el-table-column>
      <el-table-column prop="email" label="邮箱"> </el-table-column>
      <el-table-column align="center" width="180" label="操作" v-if="parms.username !== 'admin'">
        <template slot-scope="scope" >
          <el-button
            type="primary"
            size="small"
            @click="editBtn(scope.row)"
            icon="el-icon-edit"
          ></el-button>
          <el-button
            type="danger"
            size="small"
            @click="deleteBtn(scope.row)"
            icon="el-icon-delete"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 
     @size-change页容量改变时触发事件  
    @current-change页数改变时触发事件  
    :current-page当前第几页  
    :page-sizes 页数间隔数组第一个元素注意要和pagesize定义一样-->
    <el-pagination
      align="center"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="parms.currentPage"
      :page-sizes="[20, 40, 80, 100]"
      :page-size="parms.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="parms.total"
      background
    >
    </el-pagination>

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
import { addApi, getListApi, editApi,deleteApi } from "@/api/user";

export default {
  //注册要引用的组件
  components: { SysDialog },

  //页面展示数据定义
  data() {
    return {
      //表格高度
      tableHeight: 0,
      //表格的数据
      tableList: [],
      //弹框属性
      addDialog: {
        title: "",
        width: 650,
        height: 250,
        visible: false,
      },
      parms: {
        username: "",
        title: "",
        currentPage: 1, //从第几页开始
        pageSize: 20, //每页查询条数
        total: 0, //总条数
      },
      addModel: {
        userId: "",
        editType: "", //0,新增；1，编辑
        phone: "",
        email: "",
        username: "",
        password: "",
      },
      rules: {
        phone: [
          {
            trigger: "change",
            message: "请填写电话",
            required: true,
          },
        ],
        email: [
          {
            trigger: "change",
            message: "请填写邮箱",
            required: true,
          },
        ],
        username: [
          {
            trigger: "change",
            message: "请填写账户",
            required: true,
          },
        ],
        password: [
          {
            trigger: "change",
            message: "请填写密码",
            required: true,
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 200;
    });
  },
  methods: {
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //重置按钮
    resetBtn() {
      this.parms.username = "";
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.parms.pageSize = val;
      this.parms.currentPage = 1;
      //重新获取列表
      this.getList();
    },
    //页数改变时触发
    currentChange(val) {
      this.parms.currentPage = val;
      //重新获取列表
      this.getList();
    },
    //编辑当前行
    editBtn(row) {
      console.log(row);
      this.$resetForm("addForm", this.addModel);
      //设置弹框属性
      this.addDialog.title = "编辑用户";
      this.addDialog.visible = true;
      //设置编辑数据回显，把当前编辑的数据设置到表单绑定的数据域
      this.$objCoppy(row, this.addModel);
      //设置为编辑
      this.addModel.editType = "1";
    },
    //删除当前行
    async deleteBtn(row) {
      console.log(row);
      //信息确认提示
      let confirm = await this.$myconfirm('确定删除该数据吗？')
      if(confirm){
        let res = await deleteApi({userId:row.userId})
        if(res && res == 200){
          //信息提示and刷新表格
          this.$message().success(res.msg);
          this.getList()
        }
      }
    },

    //获取表格列表
    async getList() {
      let res = await getListApi(this.parms);
      console.log(res);
      if (res && res.code == 200) {
        //给表格数据赋值
        this.tableList = res.data.records;
        this.parms.total = res.data.total;
      }
    },
    addBtn() {
      this.$resetForm("addForm", this.addModel);
      this.addModel.editType = "0";
      this.addDialog.title = "新增用户";
      this.addDialog.visible = true;
    },
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          //判断新增还是编辑
          if (this.addModel.editType == "0") {
            res = await addApi(this.addModel);
          } else {
            res = await editApi(this.addModel);
          }
          console.log(res);
          if (res && res.code == 200) {
            //信息提示
            this.$message.success(res.msg);
            //刷新列表
            this.getList();
            this.addDialog.visible = false;
          }
        }
      });
    },
    onClose() {
      this.addDialog.visible = false;
    },
  },
};
</script>

<style lang="scss" scoped>
</style>