<template>
  <div class="registerDiv">
    <div class="registerContent">
      <h2 class="registerTitle">用户注册</h2>
      <el-form ref="registerForm"
               :model="registerForm"
               label-width="100px"
               :rules="rules">
        <el-form-item prop="uname" label="名称">
          <el-input v-model="registerForm.uname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="registerForm.password" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="usex" label="性别">
          <el-radio-group v-model="registerForm.usex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="uage" label="年龄">
          <el-input v-model="registerForm.uage" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="地址">
          <el-input v-model="registerForm.address" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="uphone" label="电话">
          <el-input v-model="registerForm.uphone" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="pid" label="园区编号">
          <el-input v-model="registerForm.pid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="blackLogin"
                     style="margin-left:5%"
                     :disabled="blackLogin_disabled">返回登录页面</el-button>
          <el-button type="primary"
                     @click="registerToIndex"
                     :disabled="registerToIndex_disabled">注册并登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data(){
    let checkUage=(rule,value,callback) => {
      if(value>150){
        callback(new Error('年龄过大'));
      }else {
        callback();
      }
    };
    let checkDuplicate = (rule,value,callback) =>{
      if(this.registerForm.uid){
        return callback();
      }
      this.$axios.get(this.$httpUrl+'/user/findByUname?uname='+this.registerForm.uname)
          .then(res=>res.data).then(res=>{
        if (res.code!=200){
          callback();
        }else {
          callback(new Error('名称已存在'));
        }
      })
    };
    return{
      registerToIndex_disabled:false,
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
          { min: 3, max: 18, message: '长度在 3 到 18 个字符', trigger: 'blur' }
        ],
        uphone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,message: '请输入正确的手机号',trigger: 'blur' }
        ],
        pid:[
          { required: true, message: '请输入工作园区编号', trigger: 'blur' },
          { min: 1, max: 2, message: '长度在 1 到 2 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,2}$/,message: '工作园区编号必须是正整数',trigger: 'blur'},
        ]
      },
      registerForm:{
        uname:'',
        password:'',
        usex:'1',
        uage:'',
        address:'',
        uphone:'',
        role:'2',
        pid:''
      }
    }
  },
  methods:{
    blackLogin(){
      this.$router.push('/');
    },
    registerToIndex(){
      this.registerToIndex_disabled = true;
      this.$axios.post(this.$httpUrl+'/user/register', this.registerForm)
          .then(res=>res.data).then(res=>{
            console.log(res);
            if(res.code==200){
              this.$message({
                showClose: true,
                message: '数据添加成功',
                type: 'success'
              });
              //存储
              sessionStorage.setItem("CurUser",JSON.stringify(res.data));
              //跳转到主页
              this.$router.push('/index');
              this.resetForm();
            }else {
              this.registerToIndex_disabled = false;
              this.$message({
                showClose: true,
                message: '数据添加失败',
                type: 'error'
              });
          }
      });
    },
    resetForm() {
      this.$refs.form.resetFields();
    }
  }
}
</script>

<style scoped>
.registerDiv {
  margin: auto;
  width: 30%;
  height: 30%;
}
.registerTitle {
  margin-top: 5%;
  text-align: center;
}
.registerContent{
  margin-top: 20%;
  border-radius: 15px;
  border: gray 1px solid;
  box-shadow: 0 3px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
}
.el-form {
  margin-top: 5%;
}
</style>