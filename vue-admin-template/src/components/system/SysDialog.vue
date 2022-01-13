<template>
  <el-dialog
  top = "1vh"
    :title="title"
    :visible.sync="visible"
    :width="width + 'px'"
    :before-close="onClose"
  >
    <div class="container" :style="{ height: height + 'px' }">
      <slot name="content"></slot>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="danger" @click="onClose">取 消</el-button>
      <el-button type="primary" @click="onConfirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  //    接收父组件传值
  props: {
    title: {
      type: String,
      default: "标题",
    },
    width: {
      type: Number,
      default: 600,
    },
    height: {
      type: Number,
      default: 400,
    },
    visible: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    onClose() {
      //调用父组件方法
      this.$emit("onClose");
    },
    onConfirm() {
      //调用父组件方法
      this.$emit("onConfirm");
    },
  },
};
</script>

<style lang="scss" scoped>
.el-dialog__wrapper {
  ::v-deep .el-dialog {
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;
    .el-dialog__header {
      border-top-left-radius: 7px;
      border-top-right-radius: 7px;
      background-color: #1890ff;
      .el-dialog__title {
        color: #fff;
        font-size: 16px;
        font-weight: 600;
      }
      .el-dialog__close {
        color: #fff;
      }
    }
    .el-dialog__body {
      padding: 10px;
    }
    .el-dialog__footer {
      border-top: 1px solid #e8eaec;
      padding: 10px;
    }
  }
}
.container {
  overflow-x: inherit;
  overflow-y: auto;
}
</style>