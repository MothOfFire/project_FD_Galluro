<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="rid"
                placeholder="请输入仓库id"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="eid"
                placeholder="请输入设备id"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="loadPost">查询</el-button>
      <el-button type="success"
                 @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{backgroundColor:'rgba(156,180,184,0.5)'}"
              :border="true">
      <el-table-column prop="rid" label="仓库Id" width="80">
      </el-table-column>
      <el-table-column prop="rname" label="仓库名称" width="80">
      </el-table-column>
      <el-table-column prop="eid" label="设备Id" width="80">
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="80">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="submitPush(scope.row)">入库提交</el-button>
          <el-button size="small"
                     type="success"
                     @click="submitPop(scope.row)">出库提交</el-button>
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
  name: "repositoryMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData: [],
      rid: null,
      eid: null,
      pageNum:1,
      pageSize:10,
      total:0,
      actionForm: {
        actorid:null,
        power: '',
        action: '',
        actiontime: '',
        status: '',
        eid: null
      }
    }
  },
  methods: {
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
    resetForm(){
      this.actionForm.actorid = null;
      this.actionForm.power = '';
      this.actionForm.action = '';
      this.actionForm.actiontime = '';
      this.actionForm.status = '';
      this.actionForm.eid = null;
    },
    loadPost(){
      if(this.user != null){
        this.$axios.post(this.$httpUrl+'/equipment/listAllRepositoryPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                rid:this.rid,
                eid:this.eid
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
      this.rid = null;
      this.eid = null;
    },
    submitPush(row) {
      //给行为actionForm赋值
      this.actionForm.actorid = this.user.uid;
      this.actionForm.power = this.user.role;
      this.actionForm.action = '出库提交';
      this.actionForm.actiontime = new Date();
      this.actionForm.status = 'pending';
      this.actionForm.eid = row.eid;
      this.toSubmit();
      //重置
      this.$nextTick(()=>{
        this.resetForm();
      });
    },
    submitPop(row) {
      //给行为actionForm赋值
      this.actionForm.actorid = this.user.uid;
      this.actionForm.power = this.user.role;
      this.actionForm.action = '入库提交';
      this.actionForm.actiontime = new Date();
      this.actionForm.status = 'pending';
      this.actionForm.eid = row.eid;
      this.toSubmit();
      //重置
      this.$nextTick(()=>{
        this.resetForm();
      });
    },
    toSubmit() {
      this.$axios.post(this.$httpUrl+'/action/addAction', this.actionForm)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            showClose: true,
            message: '数据添加成功',
            type: 'success'
          });
          //跳转到提交页面
          this.$router.push('/submitMain');
        }else {
          this.$message({
            showClose: true,
            message: '数据添加失败',
            type: 'error'
          });
        }
      });
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>