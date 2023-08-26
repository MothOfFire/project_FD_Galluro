<template>
  <div class="body">
    <div id="gatherChart"></div>
    <div id="goodsChart"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "teaProductionMain",
  data() {
    return {
      gatherList: [],
      goodsList: []
    }
  },
  methods: {
    getGatherList() {
      this.$axios.get(this.$httpUrl+'/visualization/gatherList')
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          //获取数组,i取的是键
          for(let key in res.data) {
            let obj = {
              value: res.data[key],
              name: key
            }
            this.gatherList.push(obj);
          }
          this.getGatherChart();
        }else {
          alert("数据获取失败");
        }
      });
    },
    getGatherChart() {
      let gatherEcharts = echarts.init(document.getElementById("gatherChart"));
      let option = {
        title:{//echarts的标题
          text:'茶叶未采摘情况图',
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
            name: '茶叶未采摘情况',
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
            data: this.gatherList
          }
        ]
      }
      gatherEcharts.setOption(option);
    },
    getGoodsList() {
      this.$axios.get(this.$httpUrl+'/visualization/goodsList')
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          //获取数组,i取的是键
          for(let key in res.data) {
            let obj = {
              value: res.data[key],
              name: key
            }
            this.goodsList.push(obj);
          }
          this.getGoodsListChart();
        }else {
          alert("数据获取失败");
        }
      });
    },
    getGoodsListChart() {
      let goodsEcharts = echarts.init(document.getElementById("goodsChart"));
      let option = {
        title:{//echarts的标题
          text:'产品库存图',
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
            name: '产品库存',
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
            data: this.goodsList
          }
        ]
      }
      goodsEcharts.setOption(option);
    }
  },
  beforeMount() {
    this.getGatherList();
    this.getGoodsList();
  }
}
</script>

<style scoped>
.body {
  margin-top: 5%;
}

#gatherChart {
  width: 500px;
  height: 500px;;
  border: 1px #000 solid;
  float: left;
  margin-left: 5%;
}
#goodsChart {
  width: 500px;
  height: 500px;;
  border: 1px #000 solid;
  float: right;
  margin-right: 5%;
}
</style>