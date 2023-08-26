<template>
  <div class="body">
    <div id="trafficChart"></div>
    <div id="employeeChart"></div>
  </div>
</template>

<script>
//引用echarts
import * as echarts from 'echarts';
export default {
  name: "personnelVisualizationMain",
  data() {
    return {
      trafficList:[],
      trafficDay:[],
      day:[],
      moth:[],
      year:[],
      total:[]
    }
  },
  methods: {
    //将前端收到的date数据转换成标准格式
    changeTime(date) {
      let date0 = date.substring(0, 8);
      let date1 = date.substring(8,10);
      let date2 = (parseInt(date1) + 1).toString();
      let newDate = date0 + date2;
      return newDate;
    },
    getTrafficList() {
      this.$axios.get(this.$httpUrl+'/visualization/listTraffic')
          .then(res=>res.data).then(res=>{
            console.log(res);
            if(res.code==200){
              for (const item of res.data){
                item.date = this.changeTime(item.date);
              }
              this.trafficList = res.data;
              //获取数组
              for (let i = 0; i<res.total; i++){
                this.trafficDay.push(res.data[i].date);
                this.day.push(res.data[i].daynumber);
                this.moth.push(res.data[i].mothnumber);
                this.year.push(res.data[i].yearnumber);
              }
              this.getTrafficChart();
            }else {
              alert("数据获取失败");
            }
      });
    },
    getTrafficChart() {
      let trafficEcharts = echarts.init(document.getElementById("trafficChart"));
      let option = {
        title:{//echarts的标题
          text:'游客人流量图',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['日人流量', '月人流量', '年人流量'],
          top: '5%',
          left: 'center',
        },
        xAxis: {
          type: 'category',
          data: this.trafficDay
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {//系列
            name:'日人流量',
            type:'line',
            //stack:"traffic",//设置这个会导致折线图不准确
            data:this.day,
            //样式填充
            areaStyle:{},
            //设置高亮
            emphasis: {
              focus:'series'
            }
          },
          {
            name:'月人流量',
            type:'line',
            //stack:"traffic",
            data:this.moth,
            //样式填充
            areaStyle:{},
            //设置高亮
            emphasis: {
              focus:'series'
            }
          },
          {
            name:'年人流量',
            type:'line',
            //stack:"traffic",
            data:this.year,
            //样式填充
            areaStyle:{},
            //设置高亮
            emphasis: {
              focus:'series'
            }
          }
        ]
      }
      trafficEcharts.setOption(option);
    },
    getEmployeeTotal() {
      this.$axios.get(this.$httpUrl+'/visualization/employeeTotal')
          .then(res=>res.data).then(res=>{
        if(res.code==200){
          //获取数组,i取的是键
          for(let key in res.data) {
            let obj = {
              value: res.data[key],
              name: key
            }
            this.total.push(obj);
          }
          this.getEmployeeChart();
        }else {
          alert("数据获取失败");
        }
      });
    },
    getEmployeeChart() {
      let employeeEcharts = echarts.init(document.getElementById("employeeChart"));
      let option = {
        title:{//echarts的标题
          text:'园区人员分布图',
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
            name: '园区人员分布',
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
            data: this.total
          }
        ]
      }
      employeeEcharts.setOption(option);
    }
  },
  beforeMount() {
    this.getTrafficList();
    this.getEmployeeTotal();
  }
}
</script>

<style scoped>

 .body {
   margin-top: 5%;
 }

 #trafficChart {
   width: 500px;
   height: 500px;;
   border: 1px #000 solid;
   float: left;
   margin-left: 5%;
 }
 #employeeChart {
   width: 500px;
   height: 500px;;
   border: 1px #000 solid;
   float: right;
   margin-right: 5%;
 }
</style>