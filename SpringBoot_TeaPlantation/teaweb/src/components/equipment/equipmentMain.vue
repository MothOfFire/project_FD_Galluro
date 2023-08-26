<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="ino"
                placeholder="请输入设备项目id"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="isbad"
                 filterable placeholder="请选择是否损坏"
                 style="margin-left: 5px">
        <el-option
            v-for="item in isbads"
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
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{backgroundColor:'rgba(156,180,184,0.5)'}"
              :border="true">
      <el-table-column prop="ino" label="设备编号" width="80">
      </el-table-column>
      <el-table-column prop="eid" label="设备Id" width="80">
      </el-table-column>
      <el-table-column prop="isbad" label="是否损坏" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isbad === 'false' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isbad === 'false' ? '没损坏' : '损坏'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="ename" label="设备名称" width="120">
      </el-table-column>
      <el-table-column prop="pid" label="园区id" width="80">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="updateEquipmentItem(scope.row)">修改</el-button>
          <el-popconfirm title="确定删除吗？"
                         style="margin-left: 5px"
                         @confirm="deleteEquipmentItem(scope.row)">
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
        <el-form-item prop="eid" label="设备ID">
          <el-input v-model="form.eid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="ename" label="设备名称">
          <el-input v-model="form.ename" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="isbad" label="是否损坏">
          <el-radio-group v-model="form.isbad">
            <el-radio label="true">损坏</el-radio>
            <el-radio label="false">没损坏</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="pid" label="园区ID">
          <el-input v-model="form.pid" style="width: 80%"></el-input>
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
  name: "equipmentMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData: [],
      ino:'',
      isbad:'',
      isbads:[
        {
          value: 'true',
          label: '已损坏'
        }, {
          value: 'false',
          label: '没损坏'
        }
      ],
      pageNum:1,
      pageSize:10,
      total:0,
      centerDialogVisible:false,
      do: '',
      form: {
        ino:'',
        eid:'',
        ename: '',
        isbad: 'false',
        pid: null
      },
      rules: {
        eid: [
          {required: true, message: '请输入设备ID', trigger: 'blur'},
          { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,5}$/,message: '设备id必须是正整数',trigger: 'blur'}
        ],
        ename: [
          {required: true, message: '请输入茶树种类', trigger: 'blur'},
          { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' },
        ],
        pid: [
          {required: true, message: '请输入园区ID', trigger: 'blur'},
          { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,5}$/,message: '园区id必须是正整数',trigger: 'blur'}
        ],
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
        this.$axios.post(this.$httpUrl+'/equipment/listAllEquipmentItemPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                ino:this.ino,
                isbad:this.isbad
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
      this.form.ino = '';
      this.form.eid = '';
      this.form.ename = '';
      this.form.isbad = 'false';
      this.form.pid = null;
    },
    resetParam(){
      this.ino = '';
      this.isbad = '';
    },
    updateEquipmentItem(row) {
      if(this.user.role == '0'||this.user.role == '1'){
        this.centerDialogVisible = true;
        this.do = 'update';
        this.$nextTick(()=>{
          //赋值到表单
          this.form.ino = row.ino + '';
          this.form.eid = row.eid + '';
          this.form.ename = row.ename;
          this.form.isbad = row.isbad;
          this.form.pid = row.pid + '';
        });
      }else {
        this.$message({
          showClose: true,
          message: '权限不足，无法操作',
          type: 'error'
        });
      }
    },
    deleteEquipmentItem(row) {
      if(this.user.role == '0'||this.user.role == '1'){
        this.$axios.get(this.$httpUrl+'/equipment/deleteEquipmentItem?ino='+row.ino)
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
      this.$axios.post(this.$httpUrl+'/equipment/addEquipmentItem', this.form)
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
    },
    doUpdate(){
      this.$axios.post(this.$httpUrl+'/equipment/updateEquipmentItem', this.form)
          .then(res=>res.data).then(res=>{
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