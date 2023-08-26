<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="tid"
                placeholder="请输入茶树id"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="kid"
                placeholder="请输入茶树的种类"
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
      <el-table-column prop="tid" label="茶树Id" width="80">
      </el-table-column>
      <el-table-column prop="area" label="种植面积" width="120">
      </el-table-column>
      <el-table-column prop="isgather" label="是否可以采摘" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isgather === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isgather === 'false' ? '不可以' : '可以'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isdeinsectization" label="是否需要除虫" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isdeinsectization === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isdeinsectization === 'false' ? '不需要' : '需要'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isweed" label="是否需要除草" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isweed === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isweed === 'false' ? '不需要' : '需要'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="iswater" label="是否需要浇水" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.iswater === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.iswater === 'false' ? '不需要' : '需要'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isfertilize" label="是否需要施肥" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isfertilize === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isfertilize === 'false' ? '不需要' : '需要'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isprune" label="是否需要修剪" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isprune === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isprune === 'false' ? '不需要' : '需要'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="kid" label="茶树种类" width="120">
      </el-table-column>
      <el-table-column prop="pid" label="园区id" width="80">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="updateTeaTree(scope.row)">修改</el-button>
          <el-popconfirm title="确定删除吗？"
                         style="margin-left: 5px"
                         @confirm="deleteTeaTree(scope.row)">
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
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item prop="area" label="种植面积">
          <el-input v-model="form.area" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="kid" label="茶树种类">
          <el-input v-model="form.kid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="isdeinsectization" label="除虫">
          <el-radio-group v-model="form.isdeinsectization">
            <el-radio label="true">需要</el-radio>
            <el-radio label="false">不需要</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="isweed" label="除草">
          <el-radio-group v-model="form.isweed">
            <el-radio label="true">需要</el-radio>
            <el-radio label="false">不需要</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="iswater" label="浇水">
          <el-radio-group v-model="form.iswater">
            <el-radio label="true">需要</el-radio>
            <el-radio label="false">不需要</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="isfertilize" label="施肥">
          <el-radio-group v-model="form.isfertilize">
            <el-radio label="true">需要</el-radio>
            <el-radio label="false">不需要</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="isgather" label="采摘">
          <el-radio-group v-model="form.isgather">
            <el-radio label="true">可以</el-radio>
            <el-radio label="false">不可以</el-radio>
          </el-radio-group>
        </el-form-item>
          <el-form-item prop="isprune" label="修剪">
            <el-radio-group v-model="form.isprune">
              <el-radio label="true">需要</el-radio>
              <el-radio label="false">不需要</el-radio>
            </el-radio-group>
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
  name: "teaTreesMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData: [],
      tid: '',
      kid: '',
      pageNum:1,
      pageSize:10,
      total:0,
      centerDialogVisible:false,
      do: '',
      form: {
        tid:'',
        area:'',
        kid: '',
        isdeinsectization: 'true',
        iswater: 'true',
        isweed: 'true',
        isgather: 'false',
        isfertilize: 'true',
        isprune: 'false',
        pid: 3
      },
      rules: {
        area: [
          {required: true, message: '请输入种植面积', trigger: 'blur'},
          { min: 1, max: 7, message: '长度1-7个字符', trigger: 'blur' }
        ],
        kid: [
          {required: true, message: '请输入茶树种类', trigger: 'blur'}
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
    add() {
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
    loadPost(){
      if(this.user != null){
        this.$axios.post(this.$httpUrl+'/teaPark/listAllTeaTreePage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                tid:this.tid,
                kid:this.kid
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
    resetForm(){
      this.form.tid = '';
      this.form.area = '';
      this.form.kid = '';
      this.form.isdeinsectization = 'true';
      this.form.iswater = 'true';
      this.form.isweed = 'true';
      this.form.isgather = 'false';
      this.form.isfertilize = 'true';
      this.form.isprune = 'false';
      this.form.pid = 3;
    },
    resetParam(){
      this.tid = '';
      this.kid = '';
    },
    updateTeaTree(row) {
      if(this.user.role == '0'||this.user.role == '1'){
        this.centerDialogVisible = true;
        this.do = 'update';
        this.$nextTick(()=>{
          //赋值到表单
          this.form.tid = row.tid + '';
          this.form.area = row.area + '';
          this.form.kid = row.kid;
          this.form.isdeinsectization = row.isdeinsectization;
          this.form.iswater = row.iswater;
          this.form.isgather = row.isgather;
          this.form.isfertilize = row.isfertilize;
          this.form.isprune = row.isprune;
        });
      }else {
        this.$message({
          showClose: true,
          message: '权限不足，无法操作',
          type: 'error'
        });
      }
    },
    deleteTeaTree(row) {
      if(this.user.role == '0'||this.user.role == '1'){
        this.$axios.get(this.$httpUrl+'/teaPark/deleteTeaTree?tid='+row.tid)
            .then(res=>res.data).then(res=>{
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
    save() {
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
      this.$axios.post(this.$httpUrl+'/teaPark/addTeaTree', this.form)
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
      console.log(this.form);
      this.$axios.post(this.$httpUrl+'/teaPark/updateTeaTree', this.form)
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
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>