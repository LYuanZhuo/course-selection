<template>
  <div style="padding: 20px;width:800px;">
    <el-form :model="form" :rules="rules" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="年度">
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
      <el-form-item label="名字" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="详情" prop="detail">
        <el-input v-model="form.detail"></el-input>
      </el-form-item>
      <el-form-item label="选课时间" prop="time">
        <el-date-picker
          value-format="yyyy-MM-dd HH:mm"
          @change="timeSelect"
          v-model="time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="选课年级">
        <el-checkbox-group v-model="selectedGrades" size="small" @change="">
          <el-checkbox v-for="item in grades" :key="item" :label="item"  border></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="选课最大数量">
        <el-input-number style="width: 120px;" v-model="form.maxSelectNum"  :min="1" :max="10" label="选课最大数量"></el-input-number>
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
        name: "item_add",
        data() {
            let checkTime = (rule, value, callback) => {
                if (this.time == null) {
                    callback(new Error('还没选择选课时间'))
                } else {
                    callback();
                }
            }
            return {
                btn_text: '添加',
                operation: 'add',
                form: {
                    id: '',
                    year: null,
                    quarter: '',
                    name: '',
                    detail: '',
                    startTime: '',
                    endTime: '',
                    enable: false,
                    grades:'',
                    maxSelectNum:0
                },
                grades: [],
                selectedGrades: [],
                time: [],
                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                        {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
                    ],
                    detail: [
                        {required: false, message: '请输入介绍', trigger: 'change'}
                    ],
                    time: [
                        {validator: checkTime, trigger: 'change'}
                    ],
                }
            };
        },
        created: function () {
            this.$global.setNow(this.form)
            this.grades = this.$global.getGrades()
            let id = this.$route.query.id;
            if (id == undefined) {
                return;
            }
            this.setUpdate(id);//‘修改’状态
        },
        methods: {
            timeSelect() {
                if (this.time == null) {
                    return
                }
                this.form.startTime = this.time[0]
                this.form.endTime = this.time[1]
            },
            setUpdate(id) {
                this.getById(id)
                this.operation = 'update';
                this.btn_text = '修改'
            },
            getById(id) {
                this.$axios.get('item/' + id).then((res) => {
                    console.log("getById", res)
                    let item = res.data.data;
                    this.form = item;
                    let time = [item.startTime, item.endTime]
                    this.time = time
                    this.selectedGrades = this.form.grades.split(",")
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.form.grades = this.selectedGrades.toString()
                        if (this.operation == 'update') {
                            this.$axios.put('item/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('item_list')
                            })
                        } else if (this.operation == 'add') {
                            this.$axios.post('item/', this.form).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('item_list')
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
                this.time = []
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
