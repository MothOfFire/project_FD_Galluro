<template>
  <div class="body">
    <div class="heard" style="margin-bottom: 20px;">
      <el-select v-model="makekind"
                  filterable placeholder="请选择茶叶种类"
                  style="margin-left: 5px">
      <el-option
          v-for="item in makekinds"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
      <el-button
          type="primary"
          @click="getProcessList"
          style="margin-left: 5px;">查询</el-button>
      <el-button type="success"
                 @click="resetParam">重置</el-button>
      <el-input v-model="ready"
                placeholder="请输入完成的面积或重量"
                style="margin-left: 5px;width: 200px"></el-input>
      <el-button type="primary"
                 style="margin-left: 5px;"
                 @click="next">下一步</el-button>
    </div>
    <div class="stepBody">
      <el-steps :active="active"
                align-center
                finish-status="success"
                direction="vertical">
        <el-step v-for="(value,key) in this.processList">
          <template slot="description">
            <div>
              {{value.workname}}
            </div>
            <div>
              <el-progress :percentage="value.speed" :color="customColors"></el-progress>
            </div>
          </template>
        </el-step>
      </el-steps>
    </div>
  </div>
</template>

<script>
export default {
  name: "teaProcessMain",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      makekind: '乌龙茶',
      makekinds:[
        {
          value: '乌龙茶',
          label: '乌龙茶'
        },
        {
          value: '白茶',
          label: '白茶'
        }
      ],
      speed: null,
      ready:'',
      workidList: [],
      active: 0,
      processList:[],
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
    resetParam() {
      this.makekind = '';
      this.speed = '';
    },
    getProcessList() {
      this.$axios.get(this.$httpUrl+'/teaLeaf/getProcessList?makekind='+this.makekind)
          .then(res => res.data).then(res => {
        if (res.code == 200) {
          this.processList = res.data;
          for(let item of this.processList) {
            this.workidList.push(item.workid);
          }
          this.active = 0;
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
    next() {
      this.$axios.post(this.$httpUrl+'/teaLeaf/updateWorkSpeed',
          {
            readyweight:this.ready,
            workid:this.workidList[this.active]
          }
      ).then(res=>res.data).then(res=>{
        console.log(res);
        if(res.code==200){
          if(res.data.speed === 100){
            this.processList[this.active].speed = 0;
            if(this.active++ >= this.processList.length){
              this.active = 0;
            }
          } else {
            this.processList[this.active].speed = res.data.speed;
          }
          this.ready = null;
        }else {
          alert("数据获取失败");
        }
      });
    }
  },
  beforeMount() {
    this.getProcessList();
  }
}
</script>

<style scoped>
  .stepBody {
    width: 90%;
    height: 90%;
    margin-left: 3%;
    padding: 2%;
    border-radius: 15px;
    border: 1px gray solid;
    box-shadow: 0 3px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
  }
</style>