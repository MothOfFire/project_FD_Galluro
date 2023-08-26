<template>
<div class="personalCenterDiv">
    <div class="title">
      <h2>{{user.uname}}的个人中心</h2>
    </div>
  <el-descriptions class="tablePersonal" :column="1" size="48" border>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-claim"></i>
        <span>名称</span>
      </template>
      <span>{{user.uname}}</span>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-claim"></i>
        <span>性别</span>
      </template>
      <el-tag :type="user.usex === '1'?'primary':'danger'"
              disable-transitions>
        <i :class="user.usex === '1'?'el-icon-male':'el-icon-female'"></i>
      </el-tag>
      <span>{{user.usex=='1'?"男":"女"}}</span>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-claim"></i>
        <span>年龄</span>
      </template>
      <span>{{user.uage}}</span>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-home"></i>
        <span>地址</span>
      </template>
      <span> {{user.address}}</span>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-phone"></i>
        <span>电话</span>
      </template>
      <span>{{user.uphone}}</span>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-pie-chart"></i>
        <span>工作园区编号</span>
      </template>
      <span>{{user.pid}}</span>
    </el-descriptions-item>
  </el-descriptions>
</div>
</template>

<script>
export default {
  name: "personalCenter",
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem("CurUser"))
    }
  },
  methods:{

  }
}
</script>

<style scoped>
 .personalCenterDiv {
   width: 90%;
   height: 75%;
   margin-top: 5%;
   margin-left: 5%;
   background-color: white;
   border-radius: 15px;
   border: gray 1px solid;
   box-shadow: 0 3px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
 }
  .title {
    margin-top: 2%;
    text-align: center;
    color: #bac0c1;
  }
  .tablePersonal {
    margin-top: 2%;
    width: 90%;
    margin-left: 5%;
  }
</style>