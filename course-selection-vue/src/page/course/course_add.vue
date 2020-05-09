<template>
  <div style="padding: 20px;width:800px;">
    <el-dialog title="基础课程选择" :visible.sync="basecourse_dialogTableVisible" width="1000px" >
      <basecourse_select @getBasecourse="getBasecourse"  ></basecourse_select>
    </el-dialog>
    <el-dialog title="教室选择" :visible.sync="classroom_dialogTableVisible" width="1000px">
      <classroom_select @getClassroom="getClassroom" :time="form" :open="classroom_dialogTableVisible"></classroom_select>
    </el-dialog>
    <el-form :model="form" :rules="rules" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="学年" prop="year">
        <el-date-picker
          value-format="yyyy"
          v-model="form.year"
          type="year"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="季度">
        <el-select v-model="form.quarter" placeholder="请选择">
          <el-option
            v-for="item in [{value:'秋季'},{value: '春季'}]"
            :key="item.value"
            :label="item.value"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基础课程" prop="basecourse">
        <span v-text="courseName"></span>
        <el-button type="text" @click="basecourse_dialogTableVisible = true">点击选择</el-button>
      </el-form-item>
      <el-form-item label="周">
        <el-select v-model="form.startWeek" placeholder="请选择">
          <el-option
            v-for="item in 25"
            :key="item"
            :label="'第'+item+'周'"
            :value="item">
          </el-option>
        </el-select>
        <el-select v-model="form.endWeek" placeholder="请选择">
          <el-option
            v-for="item in 25"
            :key="item"
            :label="'第'+item+'周'"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间">
        <el-select v-model="form.weekDay" placeholder="请选择">
          <el-option
            v-for="item in days"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="form.classIndex" placeholder="请选择">
          <el-option
            v-for="item in classIndexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="容量">
        <el-input-number v-model="form.count" :step=10></el-input-number>
      </el-form-item>
      <el-form-item label="详情" prop="detail">
        <el-input v-model="form.detail" type="textarea"></el-input>
      </el-form-item>

      <el-form-item label="教室" prop="classroom">
        <span v-text="classroomName"></span>
        <el-button type="text" @click="classroom_dialogTableVisible = true">点击选择</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('myForm')">{{btn_text}}</el-button>
        <el-button @click="resetForm('myForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    import Basecourse_select from "../../components/basecourse_select";
    import Classroom_select from "../../components/classroom_select";

    export default {
        name: 'course_add',
        components: {Classroom_select, Basecourse_select,},
        data() {
            return {
                btn_text: '添加',
                operation: 'add',
                basecourse_dialogTableVisible: false,
                classroom_dialogTableVisible: false,
                classroomName:'',
                courseName:'',
                form: {
                    id: 0,
                    year: null,
                    quarter: '',
                    startWeek: 1,
                    endWeek: 15,
                    weekDay: 1,
                    classIndex: 1,
                    count: 10,
                    detail:'',
                    selectCount: 0,
                    classroomId: 0,
                    basecourseId: 0,
                },
                days: [
                    {
                        label: '星期一',
                        value: 1
                    },
                    {
                        label: '星期二',
                        value: 2
                    },
                    {
                        label: '星期三',
                        value: 3
                    },
                    {
                        label: '星期四',
                        value: 4
                    },
                    {
                        label: '星期五',
                        value: 5
                    },
                    {
                        label: '星期六',
                        value: 6
                    },
                    {
                        label: '星期日',
                        value: 7
                    },
                ],
                classIndexs: [
                    {
                        label: '第1,2节',
                        value: 1
                    },
                    {
                        label: '第3,4节',
                        value: 2
                    },
                    {
                        label: '第5,6节',
                        value: 3
                    },
                    {
                        label: '第7,8节',
                        value: 4
                    },
                    {
                        label: '第9,10节',
                        value: 5
                    },
                ],
                basecourse: {},
                classroom: {},
                rules: {
                    year:
                        {required: true, message: '请选择学年', trigger: 'blur'},
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
            }
                ;
        },
        created(){
            this.$global.setNow(this.form)
            let id = this.$route.query.id;
            if (id == undefined) {
                return;
            }
            this.setUpdate(id);//‘修改’状态
        },
        methods: {
            getBasecourse(basecourse) {
                console.log('拿到基础课程：', basecourse)
                this.basecourse = basecourse
                this.courseName = basecourse.name
                this.form.basecourseId = basecourse.id
                this.basecourse_dialogTableVisible = false
            },
            getClassroom(classroom) {
                console.log('拿到教室：', classroom)
                this.classroom = classroom
                this.classroomName = classroom.name
                this.form.classroomId = classroom.id
                this.classroom_dialogTableVisible = false
            },
            setUpdate(id) {
                this.getById(id)
                this.operation = 'update';
                this.btn_text = '修改'
            },
            getById(id) {
                this.$axios.get( 'course/' + id).then((res) => {
                    console.log("getById", res)
                    let course = res.data.data;
                    this.form = course;
                    this.form.year = course.year + "";
                    this.classroomName = course.classroomName
                    this.courseName = course.courseName
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.operation == 'update') {
                            this.$axios.put( 'course/', this.form).then((res) => {
                                if (res.data.code!=0){
                                    this.$message({
                                        message: res.data.info,
                                        type: 'error'
                                    })
                                }else{
                                    this.$message({
                                        message: res.data.info,
                                        type: 'success'
                                    })
                                    this.$router.push('course_list')
                                }
                            })
                        } else if (this.operation == 'add') {
                            this.$axios.post( 'course/', this.form).then((res) => {
                                if(res.data.code==0){
                                    this.$message({
                                        message: res.data.info,
                                        type: 'success'
                                    })
                                    this.$router.push('course_list')
                                }else{
                                    this.$message({
                                        message: res.data.info,
                                        type: 'error'
                                    })
                                }
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
                this.classroomName = ''
                this.courseName=''
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
