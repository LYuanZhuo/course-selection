<template>
  <div style="padding: 20px;width:800px;">
    <el-form :model="form" :rules="rules" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="form.studentId" type="number"></el-input>
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-select v-model="form.grade" placeholder="请选择年级" style="width: 300px">
          <el-option v-for="grade in this.grades" :label="grade" :value="grade"
                     :key="grade"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学院" prop="college">
        <el-select v-model="form.college" placeholder="请选择学院" style="width: 300px">
          <el-option v-for="college in this.$global.colleges" :label="college" :value="college"
                     :key="college"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选课积分" prop="score">
        <el-input-number v-model="form.score"  :min="0" :max="100" label="选课积分"></el-input-number>
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
                grades:[],
                form: {
                    id: '',
                    name: '',
                    college: '',
                    studentId: '',
                    grade: '',
                    password: '',
                    score:30
                },
                rules: {
                    name: [
                        {required: true, message: '请输入学生姓名', trigger: 'blur'},
                        {min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'change'}
                    ],
                    college: [
                        {required: true, message: '请选择学院', trigger: 'change'}
                    ],
                    studentId: [
                        {required: true, message: '请输入学号', trigger: 'change'},
                        {min:11,max:13,message: '请输入正确的学号', trigger: 'change'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'change'}
                    ],
                    grade: [
                        { required: true, message: '请输入学号', trigger: 'change'}
                    ],
                    score: [
                        { required: true, message: '请输入选课积分', trigger: 'change'}
                    ]
                }
            };
        },
        created: function () {
            this.grades = this.$global.getGrades()
            let id = this.$route.query.id;
            if (id == undefined) {
                return;
            }
            this.setUpdate(id);//‘修改’状态
        },
        methods: {
            setUpdate(id) {
                this.getById(id)
                this.operation = 'update'
                this.btn_text = '修改'
            },
            getById(id) {
                this.$axios.get( 'user/' + id).then((res) => {
                    console.log("getById", res)
                    this.form = res.data.data;
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.operation == 'update') {
                            this.$axios.put( 'user/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('user_list')
                            })
                        } else if (this.operation == 'add') {
                            this.$axios.post( 'user/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('user_list')
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
                this.$refs.upload.fileList.splice(0, this.$refs.upload.fileList.length);
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
