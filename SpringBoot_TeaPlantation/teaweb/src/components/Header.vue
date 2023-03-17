<template>
  <div style="display: flex;line-height: 60px" >
    <div style="font-size: 25px;line-height: 60px;">
      <i :class="icon_name"
         style="cursor: pointer"
         @click="collapse">
      </i>
    </div>
    <div style="flex: 1;text-align: center;font-size:34px">
      <span>欢迎{{ user.uname }}进入茶园景观管理平台</span>
    </div>
    <el-dropdown>
      <span style="cursor: pointer">{{ user.uname }}</span>
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
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem("CurUser"))
    }
  },
  props:{
    icon_name:String
  },
  methods:{
    toUser(){
      alert("to_User");
    },
    loginOut(){
      this.$confirm('您确定要离开我吗？','提示',{
        confirmButtonText:'残忍拒绝挽留，确定登出',//确认按钮文字显示
        cancelButtonText:'拒绝登出',//取消按钮的文字显示
        type:'warning',
        center:true,//文字居中
        showCancelButton:true,//不显示取消按钮
        showClose:true,//是否显示左上角的x
        closeOnClickModal:true,//是否可以点击空白处关闭弹窗
      }).then(()=>{
        this.$message({
          type:"success",
          message:"退出登入成功"
        });
        this.$router.push('/');
        sessionStorage.clear();
      }).catch(()=>{
        this.$message({
          type:"info",
          message:"取消登出，大哥哥(姐姐)欢迎回来"
        });
      });
    },
    collapse(){
      this.$emit('doCollapse');
    }
  }
}
</script>

<style scoped>

</style>