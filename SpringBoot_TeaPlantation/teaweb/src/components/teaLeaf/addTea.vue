<template>
  <div class="body">
    <div class="top">
      <h3>{{user.uname}}添加茶叶产品信息</h3>
      <div>
        <el-form ref="form" :model="goodsForm" :rules="rules" label-width="80px">
          <el-form-item prop="picture" label="图片">
            <el-upload
                class="avatar-uploader"
                :action="this.$httpUrl+'/file/upload'"
                accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"
                :show-file-list="false"
                :on-success="uploadSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="goodsForm.picture"
                   :src="goodsForm.picture"
                   class="avatar" ref="img1">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item prop="gname" label="名称">
            <el-input v-model="goodsForm.gname" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="packType" label="包装方式">
            <el-radio-group v-model="goodsForm.packtype">
              <el-radio label="1">散装</el-radio>
              <el-radio label="0">袋装</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="price" label="单价">
            <el-input v-model="goodsForm.price" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="stock" label="库存">
            <el-input v-model="goodsForm.stock" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item prop="characterize" label="描述">
            <el-input type="textarea" v-model="goodsForm.characterize" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="button">
      <el-button type="success" @click="resetForm">重 置</el-button>
      <el-button type="primary" @click="add">确 定</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "addTea",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem("CurUser")),
      goodsForm: {
        gname:'',
        price:null,
        stock:null,
        packtype:'0',
        characterize:'',
        picture:'',
        likes:0
      },
      rules: {

      }
    }
  },
  methods: {
    uploadSuccess(res) {
      console.log(res);
      if (res.code === 200){
        this.goodsForm.picture = require("@/assets"+res.data);
        console.log(this.goodsForm);
        this.$message({
          message: '图片上传成功',
          type: 'success'
        });
      }
      else{
        console.log("上传文件失败了");
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    resetForm() {
      this.goodsForm.gname = '';
      this.goodsForm.price = null;
      this.goodsForm.stock = null;
      this.goodsForm.packtype = '0';
      this.goodsForm.characterize = '';
      this.goodsForm.picture = '';
    },
    add() {
      if(this.goodsForm.packtype === '1'){
        this.goodsForm.packtype = '散装';
      }
      if (this.goodsForm.packtype === '0') {
        this.goodsForm.packtype = '袋装';
      }
      this.$axios.post(this.$httpUrl+'/teaLeaf/addGoods', this.goodsForm)
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
    }
  }
}
</script>

<style scoped>
.body {
  width: 70%;
  height: 100%;
  margin-left: 15%;
  background-color: white;
  border-radius: 15px;
  border: gray 1px solid;
  box-shadow: 0 3px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.top {
  width: 80%;
  height: 80%;
  margin-top: 5%;
  margin-left: 10%;
}
h3 {
  height: 10%;
  width: 100%;
  text-align: center;
  margin-bottom: 2%;
}
.button {
  width: 60%;
  height: 10%;
  margin-left: 35%;
  margin-top: 3%;
}
</style>