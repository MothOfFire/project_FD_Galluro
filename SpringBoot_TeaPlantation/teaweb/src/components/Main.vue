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
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{backgroundColor:'rgba(156,180,184,0.5)'}"
              :border="true"
    >
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
      <el-table-column prop="role" label="角色" >
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.role === 0 ? 'danger' :
            (scope.row.role === 1 ? 'primary' : 'success')"
              disable-transitions>{{scope.row.role === 0 ? '超级管理员' :
              (scope.row.role === 1 ? '管理员' : '员工')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <el-button size="small" type="success">修改</el-button>
        <el-button size="small" type="danger">删除</el-button>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <!--对话框-->
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item prop="uname" label="名称">
            <el-input v-model="form.uname" style="width: 80%"></el-input>
          </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="form.password" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="usex" label="性别">
          <el-radio-group v-model="form.usex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="uage" label="年龄">
          <el-input v-model="form.uage" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="地址">
          <el-input v-model="form.address" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="uphone" label="电话">
          <el-input v-model="form.uphone" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="role" label="角色">
          <el-radio-group v-model="form.role">
            <el-radio label="0">超级管理员</el-radio>
            <el-radio label="1">管理员</el-radio>
            <el-radio label="2">员工</el-radio>
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
  name: "Main",
  data() {
    let checkUage=(rule,value,callback) => {
      if(value>150){
        callback(new Error('年龄过大'));
      }else {
        callback();
      }
    };
    let checkDuplicate = (rule,value,callback) =>{
      if(this.form.uid){
        return callback();
      }
      this.$axios(this.$httpUrl+'/user/findByUname?uname='+this.form.uname)
          .then(res=>{
            if (res.code==200){
              callback();
            }else {
              callback(new Error('名称已存在'));
            }
          })
    };
    return {
      tableData:[],
      pageNum:1,
      pageSize:5,
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
      centerDialogVisible:false,
      form:{
        uname:'',
        password:'',
        usex:'1',
        uage:'',
        address:'',
        uphone:'',
        role:'2'
      },
      rules: {
        uname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { validator: checkDuplicate,trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        uage: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须是正整数',trigger: 'blur'},
          {validator: checkUage,trigger: 'blur'}
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        uphone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,message: '请输入正确的手机号',trigger: 'blur' }
        ],
      }
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
    loadGet(){
      this.$axios.get(this.$httpUrl+'/user/listUser').then(res=>res.data).then(res=>{
        console.log(res);
        if(res.code==200){
          this.tableData = res.data;
        }else {
          alert("数据获取失败");
        }
      })
    },
    loadPost(){
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
      })
    },
    resetParam(){
      this.uname = '';
      this.usex = '';
    },
    add(){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.resetForm();
      })
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
          this.$axios.post(this.$httpUrl+'/user/addUser', this.form)
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
                }else {
                  this.$message({
                    showClose: true,
                    message: '数据添加失败',
                    type: 'error'
                  });
                  this.centerDialogVisible = false;
                }
              }
          )
        } else {
          console.log('Error!!')
          return false;
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
    }
  },
  beforeMount() {
    //this.loadGet();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>