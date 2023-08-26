<template>
  <el-container style="height: 100vh">
    <el-aside :width="aside_width"
              style="background-color: rgb(238,241,246);">
        <Aside :is_collapse="is_collapse"></Aside>
    </el-aside>

    <el-container style="height: 100vh">
      <el-header style="text-align: right; font-size: 12px;">
        <Header @doCollapse="doCollapse" :icon_name="icon_name"></Header>
      </el-header>

      <el-main style="height: 100%;background-color: #f8f8f0" >
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/basicComponents/Aside";
import Header from "@/components/basicComponents/Header";
export default {
  name:"Index",
  components: {Header, Aside},
  data(){
    return{
      is_collapse:false,
      aside_width:'200px',
      icon_name:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.is_collapse = !this.is_collapse;
      if(!this.is_collapse){
        //导航栏展开
        this.aside_width = '200px';
        this.icon_name = 'el-icon-s-fold';
      }else{
        //导航栏收起
        this.aside_width = '64px';
        this.icon_name = 'el-icon-s-unfold';
      }
    }
  }
}
</script>

<style scoped>
 .el-header {
   background-color: #bac0c1;
   color: #333;
   line-height: 60px;
 }

 .el-main{
   padding: 5px;
 }

 .el-aside {
   color: #333;
 }
 /*消除导航栏的下拉条*/
 .el-aside::-webkit-scrollbar {
   display: none;
 }
</style>