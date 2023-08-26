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
                     @click="Out(scope.row)">出园</el-button>
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
  </div>
</template>

<script>
export default {
  name: "visitorOutMain",
  data(){
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
      outtime:'',
      visitorForm: {
        vname:'',
        vsex:'1',
        vage:'',
        vphone:'',
        cid:'',
        entertime:'',
        outtime:'',
        status:'1'
      }
    }
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    //将前端收到的date数据转换成标准格式
    changeTime(date) {
      let newDate = date.substring(0, 10);
      let date2 = date.substring(12, 13);
      let date4 = date.substring(13, 19);
      let date3 = parseInt(date2) + 8;
      let newTime = newDate + " " + date3 + date4;
      return newTime;
    },
    loadPost() {
      if (this.user != null && this.user.role == '2') {
        this.$axios.post(this.$httpUrl + '/visitor/listAllPage',
            {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              param: {
                vname: this.vname.trim(),
                vsex: this.vsex
              }
            }
        ).then(res => res.data).then(res => {
          console.log(res);
          if (res.code == 200) {
            //设置表中游客时间
            for (const item of res.data) {
              console.log(this.changeTime(item.entertime));
              if (item.entertime != null) {
                item.entertime = this.changeTime(item.entertime);
              }
              if (item.outtime != null) {
                item.outtime = this.changeTime(item.outtime);
              }
            }
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("数据获取失败");
          }
        });
      } else {
        this.$confirm('未登入请登录后再进入？', '提示', {
          confirmButtonText: '确定去登入',//确认按钮文字显示
          type: 'warning',
          center: true,//文字居中
          showCancelButton: false,//不显示取消按钮
          closeOnClickModal: false,//是否可以点击空白处关闭弹窗
        }).then(() => {
          this.$router.push('/');
          sessionStorage.clear();
        }).catch(() => {
          this.$message({
            type: "info",
            message: "大哥哥(姐姐)快去登录吧！！"
          });
        });
      }
    },
    resetParam() {
      this.vname = '';
      this.vsex = '';
    },
    Out(row){
        //赋值到表单
        this.visitorForm.vid = row.vid;
        if(row.outtime == null){
          //出园时间为空时，获取系统时间赋值给该游客的出园时间
          this.visitorForm.outtime = new Date();
        }else {
          this.visitorForm.outtime = row.outtime;
        }
      this.$axios.post(this.$httpUrl + '/visitor/outVisitor',this.visitorForm)
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
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>