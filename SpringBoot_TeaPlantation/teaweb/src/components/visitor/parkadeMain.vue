<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="cid"
                placeholder="请输入车位编号"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="iscar"
                 filterable placeholder="是否空车"
                 style="margin-left: 5px">
        <el-option
            v-for="item in iscars"
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
    <div>
      <template>
        <el-row :gutter="24"  class="el-row" type="flex">
          <el-col :span="8" v-for = "(item,index) in tableData" :key="item.cid" class="el-col">
            <el-card class="el-card" :key="index">
              <div slot="header" class="clearfix">
                <h3>{{item.cid}}号车位</h3>
              </div>
              <div>
                <div class="textItem">
                  <div class="item_tag" >
                    <span >车主ID：</span>
                  </div>
                  <div class="item_desr">
                    <span > {{item.vid}}</span>
                  </div>
                </div>
                <div class="textItem">
                  <div class="item_tag" >
                    <span >车牌号：</span>
                  </div>
                  <div class="item_desr">
                    <span > {{item.carnumber}}</span>
                  </div>
                </div>
                <div class="textItem">
                  <div class="item_tag" >
                    <span >是否空位：</span>
                  </div>
                  <div class="item_desr">
                      <el-tag
                          :type="item.iscar === 'false' ? 'primary' : 'success'"
                          disable-transitions>{{item.iscar === 'false' ? '空位' : '有车'}}</el-tag>
                  </div>
                </div>
                <div class="button">
                  <el-button size="small"
                             type="success"
                             @click="updateCar(item)">登记</el-button>
                  <el-popconfirm title="确定删除吗？"
                                 style="margin-left: 25px"
                                 @confirm="outCar(item)">
                    <el-button slot="reference"
                               size="small"
                               type="danger">离开</el-button>
                  </el-popconfirm>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </template>
    </div>
    <!--对话框 centerDialogVisible用来控制对话框是否出现（true表示出现）-->
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="carForm" :rules="rules" label-width="80px">
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
  name: "parkadeMain",
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData:[],
      cid:'',
      carnumber:'',
      vid:'',
      iscar:'',
      iscars:[
        {
          value: 'false',
          label: '空车'
        },
        {
          value: 'true',
          label: '有车'
        }
      ],
      pageNum:1,
      pageSize:10,
      total:0,
      centerDialogVisible:false,
      carForm:{
        cid:'',
        iscar:'false',
        carnumber:'',
        vid:''
      },
      rules: {
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
    loadPost(){
      if(this.user != null){
        this.$axios.post(this.$httpUrl+'/car/listAllPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                cid:this.cid,
                iscar:this.iscar
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
      this.carForm.cid = '';
      this.carForm.vid = '';
      this.carForm.iscar = 'false';
      this.carForm.carnumber = '';
    },
    resetParam(){
      this.cid = '';
      this.iscar = '';
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    updateCar(row){
        this.centerDialogVisible = true;
        this.$nextTick(()=>{
          //赋值到表单
          this.carForm.cid = row.cid;
          this.carForm.vid = row.vid;
          this.carForm.carnumber = row.carnumber;
          this.carForm.iscar = row.iscar;
        });
    },
    outCar(row){
        this.carForm.cid = row.cid;
        this.carForm.carnumber = null;
        this.carForm.iscar = 'false';
        this.carForm.vid = '';
      this.$axios.post(this.$httpUrl + '/car/updateCar', this.carForm)
          .then(res => res.data).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.$message({
            showClose: true,
            message: '数据修改成功',
            type: 'success'
          });
          this.loadPost();
        } else {
          this.$message({
            showClose: true,
            message: '数据修改失败',
            type: 'error'
          });
        }
      });
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl + '/car/updateCar/43', this.carForm)
              .then(res => res.data).then(res => {
            console.log(res);
            if (res.code == 200) {
              this.$message({
                showClose: true,
                message: '数据修改成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
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
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>
 h3 {
   text-align: center;
   font-family: "Microsoft YaHei";
   color: #909399;
 }

 .button {
   margin-left: 30%;
 }

.item_tag{
  float:left;
  text-align:left;
}
.item_desr{
  margin-left: 40%;
  min-height: 30px;
  text-align:left;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.el-card {
  min-width: 100%;
  height: 100%;
  margin-right: 20px;
}
.el-card:hover{
  margin-top: -5px;
}
.el-row {
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap
}
.el-col {
  border-radius: 4px;
  align-items: stretch;
  margin-bottom: 40px;
}
</style>