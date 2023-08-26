<template>
  <div class="body">
    <div id="badNumberChart"></div>
    <div id="pushNumberChart"></div>
  </div>
</template>

<script>
export default {
  name: "resourcesVisualizationMain",
  data() {
    return {
      badNumberArray:[],
      pushNumberArray: []
    }
  },
  methods: {
    getBadNumberArray() {
      this.$axios.get(this.$httpUrl+'/visualization/badNumber')
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          //获取数组,i取的是键
          for(let key in res.data) {
            let obj = {
              value: res.data[key],
              name: key
            }
            this.badNumberArray.push(obj);
          }
          this.getBadNumberChart();
        }else {
          alert("数据获取失败");
        }
      });
    },
    getPushNumberArray() {
      this.$axios.get(this.$httpUrl+'/visualization/pushNumber')
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          //获取数组,i取的是键
          for(let key in res.data) {
            let obj = {
              value: res.data[key],
              name: key
            }
            this.pushNumberArray.push(obj);
          }
          this.getPushNumberChart();
        }else {
          alert("数据获取失败");
        }
      });
    },
    getBadNumberChart() {
      let badNumberEcharts = this.$echarts.init(document.getElementById("badNumberChart"));
      let option = {
        title:{//echarts的标题
          text:'损坏设备数量图',
          left: "center"
        },
        tooltip: {//数据触发类型
          trigger: 'item'//图形触发
        },
        legend: {
          top: '5%',
          left: 'center',
        },
        series: [
          {
            name: '损坏设备数量',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.badNumberArray
          }
        ]
      }
      badNumberEcharts.setOption(option);
    },
    getPushNumberChart() {
      let pushNumberEcharts = this.$echarts.init(document.getElementById("pushNumberChart"));
      let option = {
        title:{//echarts的标题
          text:'需要采购设备数量图',
          left: "center"
        },
        tooltip: {//数据触发类型
          trigger: 'item'//图形触发
        },
        legend: {
          top: '5%',
          left: 'center',
        },
        series: [
          {
            name: '需要采购设备数量',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.pushNumberArray
          }
        ]
      }
      pushNumberEcharts.setOption(option);
    }
  },
  beforeMount() {
    this.getBadNumberArray();
    this.getPushNumberArray();
  }
}
</script>

<style scoped>
  .body {
  margin-top: 5%;
  }

  #badNumberChart {
  width: 500px;
  height: 500px;;
  border: 1px #000 solid;
  float: left;
  margin-left: 5%;
  }
  #pushNumberChart {
  width: 500px;
  height: 500px;;
  border: 1px #000 solid;
  float: right;
  margin-right: 5%;
  }
</style>