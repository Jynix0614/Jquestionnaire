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
      <el-table-column align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="normal"
            @click="lookBtn(scope.row)"
            icon="el-icon-view"
          >查看结果</el-button>
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
    <!-- 弹框 -->
    <sys-dialog
    :title="addDialog.title"
    :height="addDialog.height"
    :width="addDialog.width"
    :visible="addDialog.visible"
    :print="addDialog.print"
    @onClose='onClose'
    @onConfirm='onConfirm'
    >
      <template slot="content">
        <div id="printTotal">
          <div style="text-align:center;font-size:20px;font-weight:600;padding:10px 0px">{{questionTitle}}</div>
          <table style="width:100%" border="1">
            <thead>
              <th>题目</th>
              <th>选项</th>
              <th>票数</th>
            </thead>
            <tbody>
              <template v-for="item in lookTableList">
                <tr v-for="(m,i) in item.paperChoice" :key='m.choiceId'>
                  <td style="width：40%;text-align:center;" v-if="i==0" :rowspan="item.paperChoice.length">{{item.paperTitle}}</td>
                  <td>{{m.choiceText}}</td>
                  <td>{{m.total}}</td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </template>
    </sys-dialog>
  </el-main>
</template>


<script>
import { getListApi, getTotalListApi } from "@/api/question";
import SysDialog from "@/components/system/SysDialog.vue";
export default {
  components: { SysDialog },
  data() {
    return {
      //表格数据的表头
      questionTitle:'',
      //统计表格的数据
      lookTableList:[],
      addDialog:{
        title:'查看调查结果',
        height:'500',
        width:'950',
        visible:false,
        print:true
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
    //查看票数
    async lookBtn(row){
      //查询统计数据
      let res = await getTotalListApi({questionId:row.questionId})
      if(res && res.code==200){
        this.lookTableList = res.data.listPaper
        this.questionTitle = res.data.questionTitle
        console.log('结果查询',res);
      }
      this.addDialog.visible = true;
      },
      
    async getList() {
      let res = await getListApi(this.parms);
      console.log(res);
      if (res && res.code == 200) {
        //表格数据赋值
        this.tableList = res.data.records;
        //分页总条数
        this.parms.total = res.data.total;
      }
      
    },
    // onConfirm() {
    //   this.$refs.addForm.validate(async (valid) => {
    //     if (valid) {
    //       let res = null;
    //       if (this.addModel.editType == "0") {
    //         res = await addApi(this.addModel);
    //       } else {
    //         res = await editApi(this.addModel);
    //       }
    //       if (res && res.code == 200) {
    //         this.getList();
    //         this.$message.success(res.msg);
    //         this.addDialog.visible = false;
    //       }
    //     }
    //   });
    // },
    //确认
    onClose() {
      this.addDialog.visible = false;
    },
    //关闭
    onConfirm() {
      this.addDialog.visible = false;
    },
  },
};
</script>

<style lang="scss" scoped>
table{
  width:300px;
  text-align: center;
  border-collapse: collapse;
}
</style>