<template>
  <el-main>
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
        <el-button style="color: #ff7670" @click="resetBtn" icon="el-icon-close"
          >重置</el-button
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
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="questionTitle"
        width="300"
        label="问卷标题"
      ></el-table-column>
      <el-table-column prop="questionDesc" label="问卷描述"></el-table-column>
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
            type="success"
            icon="el-icon-setting"
            size="small"
            @click="addPaper(scope.row)"
            >设计试题</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 
    @size-change 页容量改变时触发的事件
    @current-change 页数改变时触发的事件
    :current-page  当前第几页 
    :page-sizes 
    :page-size  没有查询几条
     :total 总条数
    -->
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
        <!-- 设置占满窗口 -->
        <el-container style="height: 100%">
          <!-- 左侧分类 -->
          <el-aside class="leftcontainer" width="200px">
            <div class="btns">
              <el-button
                @click="addPaperData('1')"
                class="btnitem"
                size="default"
                >单选题</el-button
              >
              <el-button
                @click="addPaperData('2')"
                class="btnitem"
                size="default"
                >多选题</el-button
              >
              <el-button
                @click="addPaperData('3')"
                class="btnitem"
                size="default"
                >单文本</el-button
              >
              <el-button
                @click="addPaperData('4')"
                class="btnitem"
                size="default"
                >多文本</el-button
              >
            </div>
          </el-aside>
          <!-- 右侧试题 -->
          <el-main style="padding-top: 1px">
            <el-form
              :model="customerFormModel"
              ref="customerForm"
              label-width="50px"
              :inline="false"
              :rules="rules"
              size="small"
            >
              <div
                :key="index"
                v-for="index in customerFormModel.paperDataList.length"
              >
                <!-- 单选、多选 -->
                <div
                  v-if="
                    customerFormModel.paperDataList[index - 1].paperType ==
                      '1' ||
                    customerFormModel.paperDataList[index - 1].paperType == '2'
                  "
                  class="papetitem"
                >
                  <!-- <el-row>：代表一行 ，里面的子元素会横向排列
                     <el-col ：代表的是列 
                     :span 代表所占的分数，总分数是 24
                      -->
                  <el-row>
                    <el-col :span="17" :offset="0">
                      <el-form-item
                        :label="index + '、'"
                        label-width="30px"
                        size="small"
                      >
                        <el-input
                          v-model="
                            customerFormModel.paperDataList[index - 1]
                              .paperTitle
                          "
                          size="small"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="7" :offset="0">
                      <el-button
                        style="margin-left: 15px"
                        size="small"
                        icon="el-icon-plus"
                        @click="addChoice(index)"
                        >新增选项</el-button
                      >
                      <el-button
                        style="margin-left: 15px"
                        type="danger"
                        size="small"
                        icon="el-icon-delete"
                        @click="deletePaper(index)"
                        >删除试题</el-button
                      >
                    </el-col>
                  </el-row>
                  <el-form-item
                    :key="num"
                    v-for="num in customerFormModel.paperDataList[index - 1]
                      .paperChoice.length"
                    label=""
                    size="small"
                  >
                    <el-row>
                      <el-col :span="10" :offset="0">
                        <el-form-item :label="'选项' + num" size="small">
                          <el-input
                            v-model="
                              customerFormModel.paperDataList[index - 1]
                                .paperChoice[num - 1].choiceText
                            "
                            size="small"
                          ></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="10" :offset="0">
                        <el-form-item label="序号" size="small">
                          <el-input
                            v-model="
                              customerFormModel.paperDataList[index - 1]
                                .paperChoice[num - 1].choiceOrder
                            "
                            size="small"
                          ></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="2" :offset="0">
                        <el-button
                          style="color: #ff7670; margin-left: 15px"
                          size="small"
                          circle
                          icon="el-icon-close"
                          @click="deleteChoice(index, num)"
                        ></el-button>
                      </el-col>
                    </el-row>
                  </el-form-item>
                </div>
                <!-- 单文本 -->
                <div
                  v-if="
                    customerFormModel.paperDataList[index - 1].paperType == '3'
                  "
                  class="papetitem"
                >
                  <el-row>
                    <el-col :span="17" :offset="0">
                      <el-form-item
                        :label="index + '、'"
                        label-width="30px"
                        size="small"
                      >
                        <el-input
                          v-model="
                            customerFormModel.paperDataList[index - 1]
                              .paperTitle
                          "
                          size="small"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col class="delBtn" :span="7" :offset="0">
                      <el-button
                        @click="deletePaper(index)"
                        style="margin-left: 15px"
                        type="danger"
                        icon="el-icon-delete"
                        size="small"
                        >删除试题</el-button
                      >
                    </el-col>
                  </el-row>
                </div>
                <!-- 多文本 -->
                <div
                  v-if="
                    customerFormModel.paperDataList[index - 1].paperType == '4'
                  "
                  class="papetitem"
                >
                  <el-row>
                    <el-col :span="17" :offset="0">
                      <el-form-item
                        :label="index + '、'"
                        label-width="30px"
                        size="small"
                      >
                        <el-input
                          v-model="
                            customerFormModel.paperDataList[index - 1]
                              .paperTitle
                          "
                          type="textarea"
                          size="small"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col class="delBtn" :span="7" :offset="0">
                      <el-button
                        @click="deletePaper(index)"
                        style="margin-left: 15px"
                        type="danger"
                        icon="el-icon-delete"
                        size="small"
                        >删除试题</el-button
                      >
                    </el-col>
                  </el-row>
                </div>
              </div>
            </el-form>
          </el-main>
        </el-container>
      </template>
    </sys-dialog>
  </el-main>
</template>

<script>
import { getListApi } from "@/api/question";
import SysDialog from "@/components/system/SysDialog.vue";
import { savePaperApi, getPaperListApi } from "@/api/paper";
export default {
  //注册组件
  components: {
    SysDialog,
  },
  data() {
    return {
      rules:{
        paperDataList:[
          {
            trigger: "change",
            required: true,
            message: "不能为空",
          },
        ]
      },
      //问卷id
      questionId: "",
      //试题绑定的数据域
      customerFormModel: {
        paperDataList: [],
      },
      //弹框属性
      addDialog: {
        title: "",
        width: 1080,
        height: 600,
        visible: false,
      },
      //表格的高度
      tableHeight: 0,
      //表格数据
      tableList: [],
      //列表参数
      parms: {
        currentPage: 1,
        pageSize: 20,
        questionTitle: "",
        total: 0, //分页用的总条数
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
    // 删除选项
    deleteChoice(index, num) {
      this.customerFormModel.paperDataList[index - 1].paperChoice.splice(
        num - 1,
        1
      );
    },
    //新增选择题的选项
    addChoice(index) {
      console.log(index);
      this.customerFormModel.paperDataList[index - 1].paperChoice.push({
        choiceText: "",
        choiceOrder: "",
      });
    },
    //删除试题
    deletePaper(index) {
      this.customerFormModel.paperDataList.splice(index - 1, 1);
    },
    //弹框确认事件
    async onConfirm() {
      if (this.customerFormModel.paperDataList.length < 1) {
        this.$message.warning("请设计试题!");
        return;
      }
      let res = await savePaperApi(this.customerFormModel.paperDataList);
      if (res && res.code == 200) {
        this.addDialog.visible = false;
        this.$message.success(res.msg);
      }
      console.log(this.customerFormModel.paperDataList);
    },
    //弹框关闭事件
    onClose() {
      this.addDialog.visible = false;
    },
    //设计试题按钮
    async addPaper(row) {
      console.log(row);
      //清空数据
      if (row.questionStatus == "0") {
        this.$message.warning("已关闭试题不可编辑！");
      } else {
        this.customerFormModel.paperDataList = [];
        //设置问卷id
        this.questionId = row.questionId;
        //先查询该问卷是否存在试题，如果存在，回显；
        //不存在，初始表单数据，默认展示一个单选题
        let res = await getPaperListApi({ questionId: this.questionId });
        if (res && res.code == 200) {
          console.log("试题");
          console.log(res);
          if (res.data && res.data.length > 0) {
            //设置返回的试题数据
            this.customerFormModel.paperDataList = res.data;
          } else {
            this.addPaperData("1");
          }
        } else {
          this.addPaperData("1");
        }
        //设置弹框属性
        this.addDialog.title = "为【" + row.questionTitle + "】设计试题";
        this.addDialog.visible = true;
      }
    },
    addPaperData(type) {
      //试题的数据格式，根据数据库字段所得
      let objModel = {
        paperId: "",
        questionId: "",
        paperTitle: "",
        paperType: "",
        paperOrder: "",
        //试题的选项
        paperChoice: [],
      };
      //如果是单选题或者多选题，需要设置选项
      if (type == "1" || type == "2") {
        let obj = {
          choiceText: "",
          choiceOrder: "",
        };
        objModel.paperChoice.push(obj);
      }
      //设置问卷id
      objModel.questionId = this.questionId;
      //设置试题的类型
      objModel.paperType = type;
      //设置到表单绑定的数据域
      this.customerFormModel.paperDataList.push(objModel);
    },
    //重置按钮
    resetBtn() {
      this.parms.questionTitle = "";
      this.getList();
    },
    //搜索按钮
    searchBtn() {
      this.getList();
    },
    //页数改变时触发
    currentChange(val) {
      this.parms.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.parms.pageSize = val;
      this.parms.currentPage = 1;
      this.getList();
    },
    //获取列表
    async getList() {
      let res = await getListApi(this.parms);
      if (res && res.code == 200) {
        //给表格数据赋值
        this.tableList = res.data.records;
        //分页总条数
        this.parms.total = res.data.total;
      }
      console.log(res);
    },
  },
};
</script>

<style lang="scss" scoped>
.leftcontainer {
  border-right: 5px solid #e8eaec;
}
.btns {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-right: 10px;
}
.btnitem {
  width: 100%;
  margin-left: 0px;
  margin-bottom: 10px;
}
.delBtn {
  display: flex;
  justify-content: flex-end;
  padding-right: 15px;
}
.papetitem {
  border-bottom: 1px solid #e8eaec;
  padding-top: 20px;
}
</style>
