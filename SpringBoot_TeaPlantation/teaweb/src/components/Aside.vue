<template>
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="height: 100%"
      default-active="/Home"
      :collapse="is_collapse"
      :collapse-transition="false"
  >
    <el-menu-item index="/Home">
        <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>
    <el-menu-item index="/One">
      <i class="el-icon-user"></i>
      <span slot="title">游客信息管理</span>
    </el-menu-item>
    <el-menu-item index="/Two">
      <i class="el-icon-location-information"></i>
      <span slot="title">茶园信息管理</span>
    </el-menu-item>
    <el-menu-item index="/Tree">
      <i class="el-icon-takeaway-box"></i>
      <span slot="title">茶园设备管理</span>
    </el-menu-item>
    <el-menu-item index="/Four">
      <i class="el-icon-s-data"></i>
      <span slot="title">数据可视化管理</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{

    }
  },
  props:{
    is_collapse:Boolean
  }
}
</script>

<style scoped>

</style>