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
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="add">新增</el-button>
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
      <el-table-column prop="pushnumber" label="需要入库是数量" width="120">
      </el-table-column>
      <el-table-column prop="popnumber" label="需要出库的数量" width="120">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="approvalPush(scope.row)">入库审批</el-button>
          <el-button size="small"
                     type="success"
                     @click="approvalPop(scope.row)">出库审批</el-button>
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
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item prop="rname" label="仓库名称">
          <el-input v-model="form.rname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="eid" label="设备id">
          <el-input v-model="form.eid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="stock" label="库存">
          <el-input v-model="form.stock" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "repositoryManageMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData: [],
      rid: null,
      eid: null,
      pageNum:1,
      pageSize:10,
      total:0,
      centerDialogVisible: false,
      actionForm: {
        actorid:null,
        power: '',
        action: '',
        actiontime: '',
        status: ''
      },
      form: {
        rid: null,
        rname: '',
        eid: null,
        stock: null,
        pushnumber: 0,
        popnumber: 0
      },
      rules: {
        rname:[
          {required: true, message: '请输入仓库名称', trigger: 'blur'},
          { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' },
        ],
        eid: [
          {required: true, message: '请输入设备ID', trigger: 'blur'},
          { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,5}$/,message: '年龄必须是正整数',trigger: 'blur'}
        ],
        stock: [
          {required: true, message: '请输入设备库存', trigger: 'blur'},
          { min: 1, max: 4, message: '长度在 1 到 4 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,4}$/,message: '库存必须是正整数',trigger: 'blur'}
        ]
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
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    resetActionForm(){
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
    resetForm() {
      this.form.rname = '';
      this.form.eid = null;
      this.form.stock = null;
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.resetForm();
      });
    },
    approvalPush(row) {
      //给行为actionForm赋值
      this.actionForm.actorid = this.user.uid;
      this.actionForm.power = this.user.role;
      this.actionForm.action = '出库审批';
      this.actionForm.actiontime = new Date();
      this.actionForm.status = 'resolve';
      this.actionForm.eid = row.eid;
      this.toApproval();
      //重置
      this.$nextTick(()=>{
        this.resetActionForm();
      });
    },
    approvalPop(row) {
      //给行为actionForm赋值
      this.actionForm.actorid = this.user.uid;
      this.actionForm.power = this.user.role;
      this.actionForm.action = '入库审批';
      this.actionForm.actiontime = new Date();
      this.actionForm.status = 'resolved';
      this.actionForm.eid = row.eid;
      this.toApproval();
      //重置
      this.$nextTick(()=>{
        this.resetActionForm();
      });
    },
    toApproval() {
      this.$axios.post(this.$httpUrl+'/action/addAction', this.actionForm)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            showClose: true,
            message: '数据添加成功',
            type: 'success'
          });
          //跳转到提交页面
          this.$router.push('/approvalMain');
        }else {
          this.$message({
            showClose: true,
            message: '数据添加失败',
            type: 'error'
          });
        }
      });
    },
    save() {
      this.$axios.post(this.$httpUrl+'/equipment/addRepository', this.form)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            showClose: true,
            message: '数据添加成功',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.loadPost();
          this.resetForm();
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