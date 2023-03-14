<template>
  <div style="display: flex;line-height: 60px" >
    <div style="font-size: 25px;line-height: 60px;">
      <i :class="icon_name"
         style="cursor: pointer"
         @click="collapse">
      </i>
    </div>
    <div style="flex: 1;text-align: center;font-size:34px">
      <span>欢迎进入茶园景观管理平台</span>
    </div>
    <el-dropdown>
      <span style="cursor: pointer">王小虎</span>
      <i class="el-icon-user-solid" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="loginOut">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    icon_name:String
  },
  methods:{
    toUser(){
      alert("to_User");
    },
    loginOut(){
      alert("退出登录");
    },
    collapse(){
      this.$emit('doCollapse');
    }
  }
}
</script>

<style scoped>

</style>