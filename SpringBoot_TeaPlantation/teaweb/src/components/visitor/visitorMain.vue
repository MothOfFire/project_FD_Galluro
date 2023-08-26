<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="vname"
                placeholder="请输入游客姓名"
                suffix-icon="el-icon-search"
                style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="vsex"
                 filterable placeholder="请选择性别"
                 style="margin-left: 5px">
        <el-option
            v-for="item in vsexs"
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
    <el-table :data="tableData"
              :header-cell-style="{backgroundColor:'rgba(156,180,184,0.5)'}"
              :border="true">
      <el-table-column prop="vid" label="vId" width="80">
      </el-table-column>
      <el-table-column prop="vname" label="游客姓名" width="120">
      </el-table-column>
      <el-table-column prop="vphone" label="联系方式" width="160">
      </el-table-column>
      <el-table-column prop="vsex" label="性别" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.vsex === '1' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.vsex === '1' ? '男' : '女'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="vage" label="年龄" width="80">
      </el-table-column>
      <el-table-column prop="cid" label="车位号" width="80">
      </el-table-column>
      <el-table-column prop="entertime" label="进园时间" width="180">
      </el-table-column>
      <el-table-column prop="outtime" label="出园时间" width="180">
      </el-table-column>
      <el-table-column prop="status" label="是否在园内" width="180">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.status === '1' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.status === '1' ? '已出园' : '还在园内'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small"
                     type="success"
                     @click="updateVisitor(scope.row)">修改</el-button>
          <el-popconfirm title="确定删除吗？"
                         style="margin-left: 5px"
                         @confirm="deleteVisitor(scope.row)">
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
    <!--对话框 centerDialogVisible用来控制对话框是否出现（true表示出现）-->
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
        :before-close="handleClose">
      <el-form ref="form" :model="visitorForm" :rules="rules" label-width="80px">
        <el-form-item prop="vname" label="游客姓名">
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
        <el-form-item prop="vphone" label="联系方式">
          <el-input v-model="visitorForm.vphone" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="cid" label="车位号">
          <el-input v-model="visitorForm.cid" style="width: 80%"></el-input>
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
  name: "visitorMain",
  data(){
    let checkVage=(rule,value,callback) => {
      if(value>150){
        callback(new Error('年龄过大'));
      }else {
        callback();
      }
    };
    return{
      tableData:[],
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      vname:'',
      vsex:'',
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
      cid:'null',
      pageNum:1,
      pageSize:10,
      total:0,
      centerDialogVisible:false,
      visitorForm: {
        vname:'',
        vsex:'1',
        vage:'',
        vphone:'',
        cid:'',
        entertime:'',
        outtime:'',
        status:''
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
    //将前端收到的date数据转换成标准格式
    changeTime(date){
      let newDate = date.substring(0,10);
      let date2 = date.substring(12,13);
      let date4 = date.substring(13,19);
      let date3 = parseInt(date2) + 8;
      let newTime = newDate + " " + date3 + date4;
      return newTime;
    },
    loadPost(){
      if(this.user != null && (this.user.role == '0'||this.user.role == '1')){
        this.$axios.post(this.$httpUrl+'/visitor/listAllPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                vname:this.vname.trim(),
                vsex:this.vsex
              }
            }
        ).then(res=>res.data).then(res=>{
          console.log(res);
          if(res.code==200){
            //设置表中游客时间
            for (const item of res.data){
              if(item.entertime != null){
                item.entertime = this.changeTime(item.entertime);
              }
              if(item.outtime != null){
                item.outtime = this.changeTime(item.outtime);
              }
            }
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
      this.vname = '';
      this.vsex = '';
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    updateVisitor(row){
        console.log(row);
        this.centerDialogVisible = true;
        this.$nextTick(()=>{
          //赋值到表单
          this.visitorForm.vid = row.vid;
          this.visitorForm.vname = row.vname;
          this.visitorForm.vage = row.vage+'';
          this.visitorForm.vsex = row.vsex;
          this.visitorForm.vphone = row.vphone;
          this.visitorForm.cid = row.cid+'';
        });
    },
    deleteVisitor(row){
      console.log(row);
        this.$axios.get(this.$httpUrl+'/visitor/deleteVisitor?vid='+row.vid)
            .then(res=>res.data).then(res=> {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              showClose: true,
              message: '数据删除成功',
              type: 'success'
            });
            this.loadPost();
          } else {
            this.$message({
              showClose: true,
              message: '数据删除失败',
              type: 'error'
            });
          }
        });
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl + '/visitor/updateVisitor', this.visitorForm)
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
    },
    resetForm() {
      this.$refs.visitorForm.resetFields();
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>