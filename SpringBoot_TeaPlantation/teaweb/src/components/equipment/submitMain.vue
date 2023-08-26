<template>
  <div class="submit">
    <div class="submitTop">
      <h3>提交人:{{user.uname}}</h3>
      <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item prop="eid" label="设备id">
            <el-input v-model="form.eid" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="operate" label="操作">
            <el-radio-group v-model="form.operate">
              <el-radio label = "1">出库</el-radio>
              <el-radio label = "0">入库</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="submitButton">
      <el-button type="success" @click="resetForm">重 置</el-button>
      <el-button type="primary" @click="add">确 定</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "submitMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      form: {
        eid:'',
        operate: '1'
      },
      repositoryForm: {
        rid: null,
        rname: '',
        eid: '',
        stock: null,
        pushnumber: null,
        popnumber: null
      },
      rules: {
        eid: [
          { required: true, message: '请输入设备id', trigger: 'blur' },
          { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,5}$/,message: '设备id必须是正整数',trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    resetForm(){
      this.form.eid = '';
      this.form.operate = true;
    },
    getRepositoryForm(){
      this.$axios.get(this.$httpUrl+'/equipment/selectRepositoryByEid?eid='+this.form.eid)
          .then(res => res.data).then(res => {
        if (res.code == 200) {
          this.repositoryForm.rid = res.data.rid;
          this.repositoryForm.rname = res.data.rname;
          this.repositoryForm.eid = res.data.eid;
          this.repositoryForm.stock = res.data.stock;
          this.repositoryForm.pushnumber = res.data.pushnumber;
          this.repositoryForm.popnumber = res.data.popnumber;
          if (this.form.operate == "1") {
            this.repositoryForm.popnumber += 1;
          } else {
            this.repositoryForm.pushnumber += 1;
          }
          this.toUpdate();
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
    add() {
      this.getRepositoryForm();
    },
    toUpdate(){
      this.$axios.post(this.$httpUrl+'/equipment/updateRepository', this.repositoryForm)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.resetForm();
          this.$message({
            showClose: true,
            message: '数据修改成功',
            type: 'success'
          });
        }else {
          this.$message({
            showClose: true,
            message: '数据修改失败',
            type: 'error'
          });
        }
      });
    }
  }
}
</script>

<style scoped>
  .submit {
    width: 80%;
    height: 60%;
    margin-top: 5%;
    margin-left: 10%;
  }

  .submitTop {
    height: 10%;
    width: 100%;
    text-align: center;
    margin-bottom: 2%;
  }

  .submitButton {
    width: 60%;
    height: 10%;
    margin-left: 35%;
    margin-top: 10%;
  }
</style>