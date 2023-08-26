<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="pid"
                placeholder="请输入园区id"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="pFunction"
                placeholder="请输入园区职能"
                suffix-icon="el-icon-search"
                style="width: 200px;margin-left: 5px"
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
      <el-table-column prop="pid" label="pId" width="80">
      </el-table-column>
      <el-table-column prop="pname" label="园区名称" width="120">
      </el-table-column>
      <el-table-column prop="area" label="园区面积" width="160">
      </el-table-column>
      <el-table-column prop="aid" label="园区管理员ID" width="160">
      </el-table-column>
      <el-table-column prop="function" label="园区职能" width="180">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="updatePark(scope.row)">修改</el-button>
          <el-popconfirm title="确定删除吗？"
                         style="margin-left: 5px"
                         @confirm="deletePark(scope.row)">
            <el-button slot="reference"
                       size="small"
                       type="danger">删除</el-button>
          </el-popconfirm>
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
    <!--对话框 centerDialogVisibley用来控制对话框是否出现（true表示出现）-->
    <!--超级管理员对话框-->
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item prop="pname" label="名称">
          <el-input v-model="form.pname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="area" label="面积">
          <el-input v-model="form.area" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="aid" label="管理员id">
          <el-input v-model="form.aid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="function" label="园区职能">
          <el-input v-model="form.function" style="width: 80%"></el-input>
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
  name: "teaPantationParkMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData:[],
      pageNum:1,
      pageSize:10,
      total:0,
      pid:'',
      pFunction:'',
      centerDialogVisible:false,
      do:'',
      form:{
        pname:'',
        area:'',
        aid:'',
        function:''
      },
      rules: {
        pname: [
           { required: true, message: '请输入名称', trigger: 'blur'},
        ],
        area: [
          { required: true, message: '请输入面积', trigger: 'blur' },
          { min: 1, max: 7, message: '长度1-7个字符', trigger: 'blur' },
        ],
        aid:[
          { required: true, message: '请输入管理员ID', trigger: 'blur'}
        ],
        function: [
          { required: true, message: '请输入园区职能', trigger: 'blur'}
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
    loadPost(){
      if(this.user != null){
        this.$axios.post(this.$httpUrl+'/teaPark/listAllTeaParkPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                pid:this.pid,
                function: this.pFunction
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
      this.pid = '';
    },
    add(){
      if(this.user.role == '0'||this.user.role == '1'){
        this.centerDialogVisible = true;
        this.do = 'add';
        this.$nextTick(()=>{
          this.resetForm();
        });
      }else {
        this.$message({
          showClose: true,
          message: '权限不足，无法操作',
          type: 'error'
        });
      }

    },
    updatePark(row){
      if(this.user.role == '0'||this.user.role == '1'){
        this.centerDialogVisible = true;
        this.do = 'update';
        this.$nextTick(()=>{
          //赋值到表单
          this.form.pid = row.pid;
          this.form.pname = row.pname;
          this.form.aid = row.aid;
          this.form.area = row.area+'';
          this.form.function = row.function;
        });
      }else {
        this.$message({
          showClose: true,
          message: '权限不足，无法操作',
          type: 'error'
        });
      }
    },
    deletePark(row){
      if(this.user.role == '0'||this.user.role == '1'){
        this.$axios.get(this.$httpUrl+'/teaPark/deletePark?pid='+row.pid)
            .then(res=>res.data).then(res=>{
          console.log(res);
          if(res.code==200){
            this.$message({
              showClose: true,
              message: '数据删除成功',
              type: 'success'
            });
            this.loadPost();
          }else {
            this.$message({
              showClose: true,
              message: '数据删除失败',
              type: 'error'
            });
          }
        });
      }else {
        this.$message({
          showClose: true,
          message: '权限不足，无法操作',
          type: 'error'
        });
      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.do == 'update') {
            this.doUpdate();
          }
          else if(this.do == 'add') {
            this.doSave();
          }else {

          }
        }else {
          console.log('Error!!!')
          return false;
        }
      });
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/teaPark/addPark', this.form)
          .then(res=>res.data).then(res=>{
        console.log(res);
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
    },
    doUpdate(){
      this.$axios.post(this.$httpUrl+'/teaPark/updatePark', this.form)
          .then(res=>res.data).then(res=>{
        console.log(res);
        if(res.code==200){
          this.$message({
            showClose: true,
            message: '数据修改成功',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.loadPost();
          this.resetForm();
        }else {
          this.$message({
            showClose: true,
            message: '数据修改失败',
            type: 'error'
          });
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>