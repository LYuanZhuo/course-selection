<template>
  <div style="padding: 20px;width:800px;">
    <el-dialog title="教师选择" :visible.sync="dialogTableVisible" width="1000px">
      <teacher_select v-on:getTeacher="getTeacher" :college="form.college" :open="dialogTableVisible"></teacher_select>
    </el-dialog>

    <el-form :model="form" :rules="rules" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="名字" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="详情" prop="detail">
        <el-input v-model="form.detail" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="课程类型">
        <el-select v-model="form.type" placeholder="请选择">
          <el-option
            v-for="item in ['公共选修', 'A系列','专业选修']"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学院" prop="college">
        <el-select v-model="form.college" placeholder="请选择学院" style="width: 300px">
          <el-option v-for="college in this.$global.colleges" :label="college" :value="college"
                     :key="college"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="教师" prop="teacher">
        <el-avatar v-show="this.teacher.headImage!=undefined" :fit="'cover'" shape="square" :size="100"
                   :src="teacher.headImage"></el-avatar>
        <span v-show="this.teacher.name!=undefined"><span v-text="teacher.name"></span> （<span v-text="teacher.college"></span>）</span>
        <el-button type="text" @click="dialogTableVisible = true">点击选择</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('myForm')">{{btn_text}}</el-button>
        <el-button @click="resetForm('myForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    import Teacher_select from "../../components/teacher_select";

    export default {
        components: {Teacher_select},
        data() {
            return {
                btn_text: '添加',
                operation: 'add',
                dialogTableVisible: false,
                form: {
                    id: '',
                    name: '',
                    detail: '',
                    college: '',
                    type: '',
                    teacherId: '',
                },
                teacher: {},
                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                        {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
                    ],
                    college: [
                        {required: true, message: '请选择学院', trigger: 'change'}
                    ],
                    detail: [
                        {required: false, message: '请输入介绍', trigger: 'change'}
                    ],
                    teacher: [
                        {required: false, message: '请输入介绍', trigger: 'change'}
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
            getTeacher(teacher) {
                console.log('拿到老师：', teacher)
                this.teacher = teacher
                this.form.teacherId = teacher.id
                this.dialogTableVisible = false
            },
            setUpdate(id) {
                this.getById(id)
                this.operation = 'update';
                this.btn_text = '修改'
            },
            getById(id) {
                this.$axios.get('basecourse/' + id).then((res) => {
                    console.log("getById", res)
                    let basecourse = res.data.data;
                    this.form = basecourse;
                    this.teacher.college = basecourse.teacherCollege
                    this.teacher.name = basecourse.teacherName;
                    this.teacher.headImage = basecourse.teacherHeadImage;
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.operation == 'update') {
                            this.$axios.put('basecourse/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('basecourse_list')
                            })
                        } else if (this.operation == 'add') {
                            this.$axios.post('basecourse/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('basecourse_list')
                            })
                        }
                    } else {
                        console.log('error submit!!')
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields()
                this.teacher = {}
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
