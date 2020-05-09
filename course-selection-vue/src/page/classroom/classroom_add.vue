<template>
  <div style="padding: 20px;width:800px;">
    <el-form :model="form" :rules="rules" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="教学楼" prop="building">
        <el-select v-model="form.building" placeholder="请选择教学楼" style="width: 300px" @change="createName">
          <el-option v-for="building in this.$global.buildings.concat(this.$global.colleges)" :label="building" :value="building"
                     :key="building"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="楼层" prop="floor">
        <template>
          <el-input-number v-model="form.floor" @change="createName" controls-position="right"  :min="1" :max="10"></el-input-number>
        </template>
      </el-form-item>
      <el-form-item label="教室号" prop="number">
        <el-input v-model="form.number" @change="createName"></el-input>
      </el-form-item>
      <el-form-item label="识别号" prop="name">
        <el-input v-model="form.name" disabled></el-input>
      </el-form-item>
      <el-form-item label="容纳人数" prop="count">
        <template>
          <el-input-number v-model="form.count" controls-position="right"  :step="10" :min="0" :max="1000"></el-input-number>
        </template>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input v-model="form.note"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('myForm')">{{btn_text}}</el-button>
        <el-button @click="resetForm('myForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
        data() {
            return {
                btn_text: '添加',
                operation: 'add',
                form: {
                    id: '',
                    name: '',
                    note: '',
                    floor:1,
                    size:0,
                    number:'',
                    building:''
                },

                rules: {
                    number: [
                        {required: true, message: '请选择学院', trigger: 'change'}
                    ],
                    building: [
                        {required: true, message: '请选择教学楼', trigger: 'change'}
                    ]
                }
            };
        },
        created: function () {
            let id = this.$route.query.id;
            if (id == undefined) {
                return;
            }
            this.setUpdate(id);//‘修改’状态
        },
        methods: {
            createName(){
                let building = this.form.building;
                if (building=='数学与信息学院（软件学院）'){
                    building = '数信学院'
                }
                this.form.name = building+'-'+this.form.floor+'-'+this.form.number
            },
            setUpdate(id) {
                this.getById(id)
                this.operation = 'update';
                this.btn_text = '修改'
            },
            getById(id) {
                this.$axios.get( 'classroom/' + id).then((res) => {
                    console.log("getById", res)
                    let classroom = res.data.data;
                    this.form = classroom;
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.operation == 'update') {
                            this.$axios.put( 'classroom/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('classroom_list')
                            })
                        } else if (this.operation == 'add') {
                            this.$axios.post( 'classroom/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('classroom_list')
                            })
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
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
</style>
