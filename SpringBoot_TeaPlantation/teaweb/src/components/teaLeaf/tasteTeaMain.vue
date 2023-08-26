<template>
  <div class="body">
    <div class="top">
      <el-button type="success"
                 @click="add">添加产品</el-button>
    </div>
    <div class="bot">
      <template>
        <el-row :gutter="24" class="el-row" type="flex">
          <el-col :span="8" v-for="(value, key) in goodsList" class="el-col" >
            <el-card :key="key" class="card" shadow="hover">
              <template>
                <div slot="header" class="title">
                  <h3>{{value.gname}}</h3>
                </div>
                <div class="cardBody">
                  <div class="image">
                    <img :src="value.picture" style="width: 100%;height: 100%" />
                  </div>
                  <div class="textarea">{{value.characterize}}</div>
                  <div style="margin-top: 5px">
                    <span>单价:{{value.price}}</span>
                    <br>
                    <span>库存:{{value.stock}}</span>
                  </div>
                  <div class="bottom">
                    <i :class="value.icon" @click="likes(key)"></i>
                    <span>{{ value.likes }}</span>
                  </div>
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  name: "tasteTeaMain",
  data() {
    return {
      goodsList:[],
      goodsForm: [],
    }
  },
  methods: {
    add() {
      this.$router.push('/addTea');
    },
    likes(key) {
      if(this.goodsList[key].isclick === false){
        //没点击过
        this.goodsList[key].isclick = true;
        this.goodsList[key].icon = 'el-icon-star-on';
        this.goodsList[key].likes += 1;
        //修改提交到后端的表单数据
        this.goodsForm[key].likes += 1;
      } else {
        this.goodsList[key].isclick = false;
        this.goodsList[key].icon = 'el-icon-star-off';
        this.goodsList[key].likes -= 1;
        //修改提交到后端的表单数据
        this.goodsForm[key].likes -= 1;
      }
      //修改数据库的数据
      this.$axios.post(this.$httpUrl+'/teaLeaf/updateGoods', this.goodsForm[key])
          .then(res=>res.data).then(res=>{
        if(res.code==200){
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
    },
    getGoodsLis() {
      this.$axios.get(this.$httpUrl+'/teaLeaf/getGoodsList')
          .then(res => res.data).then(res => {
        if (res.code == 200) {
          //获取数组
          let i;
          for(i=0; i < res.total; i++) {
            let obj = { };
            obj.gname = res.data[i].gname;
            obj.characterize = res.data[i].characterize;
            obj.picture = res.data[i].picture;
            obj.icon = 'el-icon-star-off';
            obj.price = res.data[i].price;
            obj.stock = res.data[i].stock;
            obj.packtype = res.data[i].packtype;
            obj.likes = res.data[i].likes;
            obj.isclick = false;
            this.goodsList.push(obj);
          }
          this.goodsForm = res.data;
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
    }
  },
  beforeMount() {
    this.getGoodsLis();
  }
}
</script>

<style scoped>
.bot {
  margin-top: 20px;
}

.card {
  width: 100%;
  height: 100%;
  margin-right: 20px;
}
.card:hover{
  margin-top: -5px;
}
.title {
  height: 10%;
  width: 100%;
  text-align: center;
  margin-bottom: 2%;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  float: right;
}

.image {
  width: 100%;
  height: 200px;
  margin-bottom: 3%;
}

.textarea {
  display: block;
  width: 90%;
  margin-left: 2%;
  border: 0;
  font-size: 15px;
  outline: none;
  border-radius: 5px;
  padding: 14px;
  background-color: #efe9db;
  word-break: break-all;
  min-height: auto;
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