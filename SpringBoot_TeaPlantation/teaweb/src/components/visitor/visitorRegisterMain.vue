<template>
  <div class="body">
    <div class="visitorTop">
      <h3>{{user.uname}}登记游客信息</h3>
      <div>
        <el-form ref="form" :model="visitorForm" :rules="rules" label-width="80px">
          <el-form-item prop="vname" label="名称">
            <el-input v-model="visitorForm.vname" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="vsex" label="性别">
            <el-radio-group v-model="visitorForm.vsex">
              <el-radio label="1">男</el-radio>
              <el-radio label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="vage" label="年龄">
            <el-input v-model="visitorForm.vage" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="cid" label="车位号">
            <el-input v-model="visitorForm.cid" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="vphone" label="电话">
            <el-input v-model="visitorForm.vphone" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="visitorButton">
      <el-button type="success" @click="resetForm">重 置</el-button>
      <el-button type="primary" @click="add">确 定</el-button>
    </div>
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="carForm" :rules="rules2" label-width="80px">
        <el-form-item prop="cid" label="车位编号">
          <el-input v-model="carForm.cid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="vid" label="车主编号">
          <el-input v-model="carForm.vid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="iscar" label="是否空车">
          <el-radio-group v-model="carForm.iscar">
            <el-radio label="true">有车</el-radio>
            <el-radio label="false">没车</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="carnumber" label="车牌号">
          <el-input v-model="carForm.carnumber" style="width: 80%"></el-input>
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
  name: "visitorRegisterMain",
  data(){
    let checkVage=(rule,value,callback) => {
      if(value>150){
        callback(new Error('年龄过大'));
      }else {
        callback();
      }
    };
    return{
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      vname:'',
      vsex:'1',
      vsexs:[
        {
          value: '1',
          label: '男'
        }, {
          value: '0',
          label: '女'
        }
      ],
      vage:'',
      vphone:'',
      cid:'',
      visitorForm:{
        vname:'',
        vsex:'1',
        vage:'',
        vphone:'',
        cid:'',
        entertime: '',
        outtime:null,
        status:'0'
      },
      rules: {
        vname: [
          { required: true, message: '请输入游客名称', trigger: 'blur' },
        ],
        vage: [
          { required: true, message: '请输入游客年龄', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须是正整数',trigger: 'blur'},
          {validator: checkVage,trigger: 'blur'}
        ],
        vphone: [
          { required: true, message: '请输入游客的联系方式', trigger: 'blur' },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,message: '请输入正确的手机号',trigger: 'blur' }
        ],
        cid:[
          { required: false, message: '请输入游客的车位号若无车位号则不用填', trigger: 'blur' },
          { min: 1, max: 2, message: '长度在 1 到 2 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,2}$/,message: '工作园区编号必须是正整数',trigger: 'blur'},
        ]
      },
      centerDialogVisible:false,
      carForm:{
        cid:'',
        iscar:'false',
        carnumber:'',
        vid:''
      },
      rules2: {
        carnumber: [
          { required: true, message: '请输入正确的车牌号', trigger: 'blur' },
          { min: 7, max: 7, message: '长度7个字符', trigger: 'blur' },
          {pattern: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/,
            trigger: 'blur'},
        ]
      }
    }
  },
  methods:{
    resetForm(){
      this.visitorForm.vname = '';
      this.visitorForm.vage = '';
      this.visitorForm.vphone = '';
      this.visitorForm.vsex = '1';
      this.visitorForm.cid = '';
    },
    getNewVid() {
      this.$axios.get(this.$httpUrl+'/visitor/getNewVid').then(res=>res.data)
          .then(res=> {
            if (res.code == 200) {
              this.carForm.vid = res.data;
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
    add(){
      this.visitorForm.entertime = new  Date();
      //判断游客是否驾车来茶园
      if(this.visitorForm.cid != ''){
        this.centerDialogVisible = true;
        this.carForm.cid = this.visitorForm.cid;
        this.getNewVid();
      }
      this.$axios.post(this.$httpUrl+'/visitor/addVisitor', this.visitorForm)
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          this.resetForm();
          this.$message({
            showClose: true,
            message: '数据添加成功',
            type: 'success'
          });
        }else {
          this.$message({
            showClose: true,
            message: '数据添加失败',
            type: 'error'
          });
        }
      });
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
          this.$axios.post(this.$httpUrl + '/car/updateCar', this.carForm)
              .then(res => res.data).then(res => {
            console.log(res);
            if (res.code == 200) {
              this.$message({
                showClose: true,
                message: '数据修改成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.resetForm();
            } else {
              this.$message({
                showClose: true,
                message: '数据修改失败',
                type: 'error'
              });
            }
          });
        }
      });
    }
  }
}
</script>

<style scoped>
 .body {
   width: 70%;
   height: 80%;
   margin-left: 15%;
   background-color: white;
   border-radius: 15px;
   border: gray 1px solid;
   box-shadow: 0 3px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
 }
 .visitorTop {
   width: 80%;
   height: 60%;
   margin-top: 5%;
   margin-left: 10%;
 }
 h3 {
   height: 10%;
   width: 100%;
   text-align: center;
   margin-bottom: 2%;
 }
 .visitorButton {
   width: 60%;
   height: 10%;
   margin-left: 35%;
   margin-top: 3%;
 }
</style>