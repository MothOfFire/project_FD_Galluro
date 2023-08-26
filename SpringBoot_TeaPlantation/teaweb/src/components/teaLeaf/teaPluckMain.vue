<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="makekind"
                placeholder="请输入茶叶种类"
                suffix-icon="el-icon-search"
                style="width: 200px"></el-input>
      <el-select v-model="gathertime"
                 filterable placeholder="请选择采摘季节"
                 style="margin-left: 5px">
        <el-option
            v-for="item in times"
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
    <div class="body">
      <template>
        <el-row :gutter="24"  class="el-row" type="flex">
          <el-col :span="8" v-for = "(value,index) in tableData" :key="value.gatherid" class="el-col">
            <el-card :key="index" shadow="hover" class="card">
              <template>
                <div slot="header" class="title">
                  <h3>{{value.gatherid}}:{{value.gathertime}}{{value.makekind}}</h3>
                </div>
                <div class="cardBody">
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>制作茶叶种类：</span>
                    </div>
                    <div class="item_desr">
                      <span>{{value.makekind}}</span>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>茶树种类：</span>
                    </div>
                    <div class="item_desr">
                      <span>{{value.kind}}</span>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>茶叶状态：</span>
                    </div>
                    <div class="item_desr">
                      <span>{{value.status}}</span>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>采摘季节：</span>
                    </div>
                    <div class="item_desr">
                      <el-tag
                          :type="value.gathertime === '春季' ? 'primary' : 'success'"
                          disable-transitions>{{value.gathertime === '春季' ? '春季' : '秋季'}}</el-tag>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>进度：</span>
                    </div>
                    <div class="item_desr">
                      <el-progress :percentage="value.speed" :color="customColors"></el-progress>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>采摘人数：</span>
                    </div>
                    <div class="item_desr">
                      <span>{{value.number}}</span>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>是否已经开始：</span>
                    </div>
                    <div class="item_desr">
                      <el-tag
                          :type="value.isbegin === 'true' ? 'primary' : 'success'"
                          disable-transitions>{{value.isbegin === 'true' ? '是' : '不是'}}</el-tag>
                    </div>
                  </div>
                  <div class="textItem">
                    <div class="item_tag" >
                      <span>是否已经结束：</span>
                    </div>
                    <div class="item_desr">
                      <el-tag
                          :type="value.isend === 'true' ? 'primary' : 'success'"
                          disable-transitions>{{value.isend === 'true' ? '是' : '不是'}}</el-tag>
                    </div>
                  </div>
                  <div class="button">
                    <el-button size="small"
                               type="success"
                               @click="updateGather(value)">修改</el-button>
                    <el-popconfirm title="确定删除吗？"
                                   style="margin-left: 5px"
                                   @confirm="deleteGather(value)">
                      <el-button slot="reference"
                                 size="small"
                                 type="danger">删除</el-button>
                    </el-popconfirm>
                  </div>
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
      </template>
    </div>
    <!--分页组件-->
    <el-pagination
        style="margin-top: 15px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[3, 6, 12, 18, 36]"
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
        <el-form-item prop="status" label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="初芽">初芽</el-radio>
            <el-radio label="嫩芽">嫩芽</el-radio>
            <el-radio label="嫩叶">嫩叶</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="gathertime" label="采摘季节">
          <el-radio-group v-model="form.gathertime">
            <el-radio label="春季">春季</el-radio>
            <el-radio label="秋季">秋季</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="makekind" label="制作种类">
          <el-input v-model="form.makekind" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="speed" label="进度">
          <el-input v-model="form.speed" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="number" label="采摘人数">
          <el-input v-model="form.number" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item prop="isbegin" label="是否开始">
          <el-radio-group v-model="form.isbegin">
            <el-radio label="true">已经开始</el-radio>
            <el-radio label="false">还没开始</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="isend" label="是否结束">
          <el-radio-group v-model="form.isend">
            <el-radio label="true">已经结束</el-radio>
            <el-radio label="false">还没结束</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="kind" label="茶树种类">
          <el-input v-model="form.kind" style="width: 80%"></el-input>
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
  name: "teaPluckMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      tableData:[],
      pageNum:1,
      pageSize:3,
      total:0,
      makekind: '',
      gathertime:'',
      times:[
        {
          value: '春季',
          label: '春季'
        }, {
          value: '秋季',
          label: '秋季'
        }
      ],
      centerDialogVisible:false,
      do: '',
      form:{
        gatherid: null,
        gathertime: '',
        status: '',
        makekind: '',
        speed: 0,
        number: null,
        isbegin: 'false',
        isend: 'false',
        kind: ''
      },
      rules: {
        makekind:[
          { required: true, message: '请输入制作的成品茶叶种类', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个字符', trigger: 'blur' },
        ],
        number: [
          { required: true, message: '请输入人数', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '人数必须是正整数',trigger: 'blur'},
        ],
        kind: [
          { required: true, message: '请输入茶树种类', trigger: 'blur' },
          { min: 1, max: 7, message: '长度在 1 到 7 个字符', trigger: 'blur' },
        ]
      },
      customColors: [
        {color: '#f56c6c', percentage: 20},
        {color: '#e6a23c', percentage: 40},
        {color: '#5cb87a', percentage: 60},
        {color: '#1989fa', percentage: 80},
        {color: '#6f7ad3', percentage: 100}
      ]
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
        this.$axios.post(this.$httpUrl+'/teaLeaf/listAllGatherPage',
            {
              pageNum:this.pageNum,
              pageSize:this.pageSize,
              param:{
                gathertime:this.gathertime,
                makekind:this.makekind
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
      this.makekind = '';
      this.gathertime = '';
    },
    add(){
        this.centerDialogVisible = true;
        this.do = 'add';
        this.$nextTick(()=>{
          this.resetForm();
        });
    },
    updateGather(row){
        this.centerDialogVisible = true;
        this.do = 'update';
        this.$nextTick(()=>{
          //赋值到表单
          this.form.gatherid = row.gatherid;
          this.form.gathertime = row.gathertime;
          this.form.status = row.status;
          this.form.makekind = row.makekind;
          this.form.kind = row.kind;
          this.form.speed = row.speed;
          this.form.number = row.number;
          this.form.isbegin = row.isbegin;
          this.form.isend = row.isend;
        });
    },
    deleteGather(row){
        this.$axios.get(this.$httpUrl+'/teaLeaf/deleteGather?gatherid='+row.gatherid)
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
          if (this.do === 'update') {
            this.doUpdate();
          }
          else if (this.do === 'add') {
            this.doSave();
          }
        }else {
          console.log('Error!!!')
          return false;
        }
      });
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/teaLeaf/addGather', this.form)
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
      this.$axios.post(this.$httpUrl+'/teaLeaf/updateGather', this.form)
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
    },
    resetForm() {
      this.form.gatherid = null;
      this.form.gathertime = '';
      this.form.status = '';
      this.form.makekind = '';
      this.form.kind = '';
      this.form.speed = 0;
      this.form.number = null;
      this.form.isbegin = 'false';
      this.form.isend = 'false';
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

.card {
  min-width: 100%;
  height: 100%;
  margin-right: 20px;
}
.card:hover{
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