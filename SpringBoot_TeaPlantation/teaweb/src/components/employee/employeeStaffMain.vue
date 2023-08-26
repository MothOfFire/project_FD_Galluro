<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="uname"
                placeholder="请输入姓名"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="usex"
                 filterable placeholder="请选择性别"
                 style="margin-left: 5px">
        <el-option
            v-for="item in usexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="loadPost">查询</el-button>
      <el-button type="success"
                 @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{backgroundColor:'rgba(156,180,184,0.5)'}"
              :border="true">
      <el-table-column prop="uid" label="uId" width="80">
      </el-table-column>
      <el-table-column prop="uname" label="名称" width="120">
      </el-table-column>
      <el-table-column prop="uphone" label="电话" width="160">
      </el-table-column>
      <el-table-column prop="usex" label="性别" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.usex === '1' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.usex === '1' ? '男' : '女'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="uage" label="年龄" width="80">
      </el-table-column>
      <el-table-column prop="address" label="地址" width="180">
      </el-table-column>
      <el-table-column prop="pid" label="园区编号" width="80">
      </el-table-column>
      <el-table-column prop="role" label="角色" >
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.role === 0 ? 'danger' :
            (scope.row.role === 1 ? 'primary' : 'success')"
              disable-transitions>{{scope.row.role === 0 ? '超级管理员' :
              (scope.row.role === 1 ? '管理员' : '员工')}}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
        style="margin-top: 15px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[ 5, 10, 15,20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "employeeStaffMain",
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem("CurUser")),
      tableData:[],
      pageNum:1,
      pageSize:10,
      total:0,
      uname:'',
      usex:'',
      usexs:[
        {
          value: '1',
          label: '男'
        }, {
          value: '0',
          label: '女'
        }
      ],
    }
  },
  methods:{
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize=val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    loadPost(){
      if(this.user != null && this.user.role == '2'){
        this.$axios.post(this.$httpUrl+'/user/listAllPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                uname:this.uname.trim(),
                usex:this.usex
              }
            }
        ).then(res=>res.data).then(res=>{
          console.log(res);
          if(res.code==200){
            this.tableData = res.data;
            this.total = res.total;
          }else {
            alert("数据获取失败");
          }
        });
      }else {
        this.$confirm('未登入请登录后再进入？','提示',{
          confirmButtonText:'确定去登入',//确认按钮文字显示
          type:'warning',
          center:true,//文字居中
          showCancelButton:false,//不显示取消按钮
          closeOnClickModal:false,//是否可以点击空白处关闭弹窗
        }).then(()=>{
          this.$router.push('/');
          sessionStorage.clear();
        }).catch(()=>{
          this.$message({
            type:"info",
            message:"大哥哥(姐姐)快去登录吧！！"
          });
        });
      }
    },
    resetParam(){
      this.uname = '';
      this.usex = '';
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>