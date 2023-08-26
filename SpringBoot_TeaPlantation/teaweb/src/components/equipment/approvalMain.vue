<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="actorid"
                placeholder="请输入行为者id"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="status"
                 filterable placeholder="请选择状态"
                 style="margin-left: 5px">
        <el-option
            v-for="item in statuss"
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
      <el-table-column prop="actorid" label="执行者Id" width="80">
      </el-table-column>
      <el-table-column prop="power" label="权限" width="80">
      </el-table-column>
      <el-table-column prop="action" label="行为" width="80">
      </el-table-column>
      <el-table-column prop="actiontime" label="执行时间" width="180">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.status === 'pending' ? 'success' :
            (scope.row.status === 'resolved' ? 'primary' : 'danger')"
              disable-transitions>{{scope.row.status === 'pending' ? '待处理' :
              (scope.row.status === 'resolved'  ? '审批通过' : '审批拒绝')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="eid" label="设备id" width="80">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="resolved(scope.row)">通过</el-button>
          <el-button size="small"
                     type="success"
                     @click="rejected(scope.row)">拒绝</el-button>
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
  name: "approvalMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData: [],
      actorid: null,
      status: '',
      statuss:[
        {
          value: 'pending',
          label: '待处理'
        },
        {
          value: 'resolved',
          label: '审批通过'
        },
        {
          value: 'rejected',
          label: '审批拒绝'
        }
      ],
      pageNum:1,
      pageSize:10,
      total:0,
      repositoryForm:{
        rid: null,
        rname: '',
        eid: '',
        stock: null,
        pushnumber: null,
        popnumber: null
      },
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
    changeTime(date){
      let newDate = date.substring(0,10);
      let date2 = date.substring(12,13);
      let date4 = date.substring(13,19);
      let date3 = parseInt(date2) + 8;
      let newTime = newDate + " " + date3 + date4;
      return newTime;
    },
    loadPost(){
      if(this.user != null){
        this.$axios.post(this.$httpUrl+'/action/listAllActionPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                actorid:this.actorid,
                status:this.status
              }
            }
        ).then(res=>res.data).then(res=>{
          if(res.code==200){
            for (const item of res.data){
              item.actiontime = this.changeTime(item.actiontime);
            }
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
      this.actorid = null;
      this.status = '';
    },
    resolved(row) {
      if (row.status === 'pending'){
        this.actionForm.eid = row.eid;
        this.actionForm.actorid = row.actorid;
        this.actionForm.action = row.action;
        this.actionForm.power = row.power;
        this.actionForm.status = 'resolved';
        console.log(this.actionForm);
        this.toApproval();
      } else {
        this.$message({
          showClose: true,
          message: '已经审批过了',
          type: 'error'
        });
      }
    },
    toApproval() {
      this.$axios.get(this.$httpUrl+'/equipment/selectRepositoryByEid?eid='+this.actionForm.eid)
          .then(res => res.data).then(res => {
        if (res.code == 200) {
          this.repositoryForm.rid = res.data.rid;
          this.repositoryForm.rname = res.data.rname;
          this.repositoryForm.eid = res.data.eid;
          this.repositoryForm.stock = res.data.stock;
          this.repositoryForm.pushnumber = res.data.pushnumber;
          this.repositoryForm.popnumber = res.data.popnumber;
          console.log(this.repositoryForm);
          this.toUpdateAction();
          this.toUpdateRepositoryFrom();
          this.$message({
            showClose: true,
            message: '成功',
            type: 'success'
          });
        } else {
          this.$message({
            showClose: true,
            message: '失败',
            type: 'error'
          });
        }
      });
    },
    rejected(row) {
      if (row.status === 'pending'){
        this.actionForm.eid = row.eid;
        this.actionForm.actorid = row.actorid;
        this.actionForm.action = row.action;
        this.actionForm.power = row.power;
        this.actionForm.status = 'rejected';
        console.log(this.actionForm);
        this.toApproval();
      } else {
        this.$message({
          showClose: true,
          message: '已经审批过了',
          type: 'error'
        });
      }
    },
    toUpdateAction() {
      this.$axios.post(this.$httpUrl+'/action/updateAction',this.actionForm)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            showClose: true,
            message: '数据修改成功',
            type: 'success'
          });
          this.loadPost();
          this.resetActionForm();
        }else {
          this.$message({
            showClose: true,
            message: '数据修改失败',
            type: 'error'
          });
        }
      });
    },
    toUpdateRepositoryFrom(){
      if (this.actionForm.status === 'resolved') {
        //审批通过
        if(this.actionForm.action === '出库提交') {
          //出库申请通过
          this.repositoryForm.popnumber -= 1;
          this.repositoryForm.stock -= 1;
        }
        if (this.actionForm.action === '入库提交') {
          //入库申请通过
          this.repositoryForm.pushnumber -= 1;
          this.repositoryForm.stock += 1;
        }
      }
      if (this.actionForm.status === 'rejected') {
        //审批拒绝
        if(this.actionForm.action === '出库提交') {
          //出库申请拒绝
          this.repositoryForm.popnumber -= 1;
        }
        if (this.actionForm.action === '入库提交') {
          //入库申请拒绝
          this.repositoryForm.pushnumber -= 1;
        }
      }
      this.toUpdateRepository();
    },
    toUpdateRepository() {
      this.$axios.post(this.$httpUrl+'/equipment/updateRepository',this.repositoryForm)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            showClose: true,
            message: '数据修改成功',
            type: 'success'
          });
          this.loadPost();
          this.resetRepositoryForm();
        }else {
          this.$message({
            showClose: true,
            message: '数据修改失败',
            type: 'error'
          });
        }
      });
    },
    resetActionForm() {
      this.actionForm.eid = null;
      this.actionForm.actorid = null;
      this.actionForm.action = '';
      this.actionForm.power = '';
      this.actionForm.actiontime = '';
      this.actionForm.status = '';
    },
    resetRepositoryForm() {
      this.repositoryForm.rid = null;
      this.repositoryForm.rname = '';
      this.repositoryForm.eid = null;
      this.repositoryForm.stock = null;
      this.repositoryForm.pushnumber = null;
      this.repositoryForm.popnumber = null;
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>