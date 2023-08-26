<template>
    <div class="loginDiv">
      <div class="loginContent">
        <h2 class="loginTitle">用户登录</h2>
        <el-form ref="loginForm"
                 :model="loginForm"
                 label-width="100px"
                 :rules="rules">
          <el-form-item prop="uname" label="名称">
            <el-input v-model="loginForm.uname"
                      style="width: 80%"
                      type="text"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="密码">
            <el-input v-model="loginForm.password"
                      style="width: 80%"
                      type="password"
                      show-password autocapitalize="off"
                      size="small" @keyup.enter.native="loginUser"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="toRegister"
                       style="margin-left:20%">注册</el-button>
            <el-button type="primary"
                       @click="loginUser"
                       :disabled="loginUser_disabled">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

</template>

<script>
export default {
  name: "Login",
  data(){
    return {
      //loginUser_disabled为true按钮点击后就不能再点击
      loginUser_disabled:false,
      loginForm:{
        uname:'',
        password:''
      },
      rules:{
        uname:[
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ]
      }
    }
  },
  methods:{
    loginUser(){
      this.loginUser_disabled = true;
      this.$refs.loginForm.validate((valid)=>{
        if (valid){//valid成功为true，失败为false
          //去后台验证名称和密码
          this.$axios.post(this.$httpUrl+'/user/login',this.loginForm)
              .then(res=>res.data).then(res=>{
                console.log(res);
                if (res.code == 200){
                  //将密码隐藏起来
                  res.data.password = null;
                  //存储
                  sessionStorage.setItem("CurUser",JSON.stringify(res.data));
                  //跳转到主页
                  this.$router.push('/personalCenter');
                }else {
                  this.loginUser_disabled = false;
                  alert('校验失败，名称或密码错误！');
                  return false;
                }
          });
        }else {
          this.loginUser_disabled = false;
          return false;
        }
      });
    },
    toRegister(){
      this.$router.push('/register');
    }
  }
}
</script>

<style scoped>

  .loginDiv {
    margin: auto;
    width: 30%;
    height: 30%;
  }
  .loginTitle {
    margin-top: 5%;
    text-align: center;
  }
  .loginContent{
    margin-top: 35%;
    border-radius: 15px;
    border: gainsboro 1px solid;
    box-shadow: 0 3px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
  }
  .el-form {
    margin-top: 5%;
  }

</style>