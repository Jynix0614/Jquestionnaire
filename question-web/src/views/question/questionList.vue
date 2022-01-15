<template>
  <el-main height="">
    <!-- Main content -->
    <!-- 模糊查询 -->
    <el-form
      :model="parms"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="normal"
    >
      <el-form-item label="问卷标题">
        <el-input v-model="parms.questionTitle" @change="searchBtn"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button style="color: #ff7670" icon="el-icon-close" @click="resetBtn"
          >重置</el-button
        >
        <el-button type="primary" @click="addBtn" icon="el-icon-plus"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格列表 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column
        prop="questionImg"
        width="130"
        label="问卷图片"
        align="center"
      >
        <template slot-scope="scope">
          <el-image
            style="width: 50px; height: 50px; border-radius: 50%"
            :src="scope.row.questionImg"
            :fit="fit"
          ></el-image>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="questionOrder" label="问卷序号"> </el-table-column> -->
      <el-table-column prop="questionTitle" width="80" label="问卷标题">
      </el-table-column>
      <el-table-column prop="questionDesc" label="问卷描述"> </el-table-column>
      <el-table-column
        prop="questionStatus"
        width="80"
        align="center"
        label="问卷状态"
      >
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.questionStatus == '0'"
            type="danger"
            size="normal"
            >已关闭</el-tag
          >
          <el-tag
            v-if="scope.row.questionStatus == '1'"
            type="success"
            size="normal"
            >调查中</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column align="center" width="180" label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="normal"
            @click="editBtn(scope.row)"
            icon="el-icon-edit"
          ></el-button>
          <el-button
            type="danger"
            size="normal"
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
    :page-sizes 页数间隔数组第一个元素注意要和pagesize定义一样
    -->
    <el-pagination
      align="center"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="parms.currentPage"
      :page-sizes="[20, 40, 80, 100]"
      :page-size="pageSize"
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
          :inline="false"
          size="normal"
        >
          <el-form-item prop="questionTitle" label="问卷标题">
            <el-input v-model="addModel.questionTitle"></el-input>
          </el-form-item>
          <el-form-item prop="questionDesc" label="问卷描述">
            <el-input
              type="textarea"
              v-model="addModel.questionDesc"
            ></el-input>
          </el-form-item>
          <el-form-item prop="questionOrder" label="问卷序号">
            <el-input v-model="addModel.questionOrder"></el-input>
          </el-form-item>
          <el-form-item prop="questionStatus" label="问卷状态">
            <el-radio-group v-model="addModel.questionStatus">
              <el-radio :label="'1'">启用</el-radio>
              <el-radio :label="'0'">停用</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="questionImg" label="问卷图片">
            <!-- el-upload
            action ：图片上传请求的地址
            :auto-upload 自动上传 
             -->
            <el-upload
              :action="onUpload()"
              :on-success="onSuccess"
              :file-list="fileList"
              list-type="picture-card"
              :auto-upload="true"
              :limit="1"
            >
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{ file }">
                <img
                  class="el-upload-list__item-thumbnail"
                  :src="file.url"
                  alt=""
                />
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="" />
            </el-dialog>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>


<script>
import { getListApi, addApi, editApi, deleteApi } from "@/api/question";
import SysDialog from "@/components/system/SysDialog.vue";
import { baseUrl, imageUrl } from "@/settings";
export default {
  components: { SysDialog },
  data() {
    return {
      fileList: [],
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      rules: {
        questionImg: [
          {
            trigger: "change",
            required: true,
            message: "请上传图片",
          },
        ],
        questionTitle: [
          {
            trigger: "change",
            required: true,
            message: "请填写标题",
          },
        ],
        questionDesc: [
          {
            trigger: "change",
            required: false,
            message: "请填写描述",
          },
        ],
        questionStatus: [
          {
            trigger: "change",
            required: true,
            message: "请选择状态",
          },
        ],
        questionOrder: [
          {
            trigger: "change",
            required: true,
            message: "请填写序号",
          },
        ],
      },
      //新增或编辑表单绑定的数据域
      addModel: {
        questionId: "",
        questionDesc: "",
        questionTitle: "",
        questionImg: "",
        questionStatus: "",
        questionOrder: "",
        editType: "", //0新增 1编辑
      },
      //弹框属性
      addDialog: {
        title: "",
        width: 630,
        height: 500,
        visible: false,
      },
      //表格高度
      tableHeight: 0,
      //表格数据
      tableList: [],
      parms: {
        currentPage: 1,
        pageSize: 20,
        questionTitle: "",
        total: 0,
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
    //图片上传成功的回调
    onSuccess(response, file, fileList) {
      this.addModel.questionImg = baseUrl + response;
      this.$message.success("图片上传成功!");
    },
    //图片上传请求方法
    onUpload() {
      return imageUrl;
    },
    //点击预览
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //点击删除
    handleRemove(file) {
      this.fileList = [];
      this.addModel.questionImg = "";
    },
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //重置按钮
    resetBtn() {
      this.parms.questionTitle = "";
      this.getList();
    },
    //页数改变时触发
    currentChange(val) {
      this.parms.currentPage = val;
      //重新获取列表
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.parms.pageSize = val;
      this.parms.currentPage = 1;
      //重新获取列表
      this.getList();
    },
    //编辑
    editBtn(row) {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      this.addModel.editType = "1";
      //设置类型，新增还是编辑
      this.addDialog.title = "编辑问卷";
      this.addDialog.visible = true;

      //清空图片
      this.fileList = [];

      // 表单数据赋值
      this.$objCoppy(row, this.addModel);
      //图片回显处理
      if (row.questionImg) {
        let obj = {};
        obj.url = row.questionImg;
        this.fileList.push(obj);
      }
    },
    //删除
    async deleteBtn(row) {
      let confirm = await this.$myconfirm("确定删除该问卷么？");
      if (confirm) {
        let res = await deleteApi({ questionId: row.questionId });
        if (res && res.code == 200) {
          //信息提示and刷新表格
          this.$message.success(res.msg);
          this.getList();
        }
      }
    },

    async getList() {
      let res = await getListApi(this.parms);
      if (res && res.code == 200) {
        //表格数据赋值
        this.tableList = res.data.records;
        //分页总条数
        this.parms.total = res.data.total;
      }
      console.log(res);
    },
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType == "0") {
            res = await addApi(this.addModel);
          } else {
            res = await editApi(this.addModel);
          }
          if (res && res.code == 200) {
            this.getList();
            this.$message.success(res.msg);
            this.addDialog.visible = false;
          }
        }
      });
    },
    onClose() {
      this.addDialog.visible = false;
    },
    //新增按钮
    addBtn() {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      this.addModel.editType = "0";
      //清空图片
      this.fileList = [];
      //设置类型，新增还是编辑
      this.addDialog.title = "新增问卷";
      this.addDialog.visible = true;
    },
  },
};
</script>

<style lang="scss" scoped>
</style>