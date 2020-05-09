<template>
  <div style="padding: 20px;width:800px;">
    <el-form :model="form" :rules="rules" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="办公室" prop="office">
        <el-input v-model="form.office"></el-input>
      </el-form-item>
      <el-form-item label="学院" prop="college">
        <el-select v-model="form.college" placeholder="请选择学院" style="width: 300px">
          <el-option v-for="college in this.$global.colleges" :label="college" :value="college"
                     :key="college"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="头像" prop="image">
        <el-upload
          ref="upload"
          style="float: left"
          action=""
          list-type="picture-card"
          :auto-upload="false"
          :on-preview="handlePictureCardPreview"
          :limit="1"
          :on-remove="handleRemove"
          :on-change="handleChange"
          :before-upload="beforeUpload"
          :show-file-list="true"
          :file-list="this.image_list"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
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
                dialogImageUrl: '',
                dialogVisible: false,
                btn_text: '添加',
                operation: 'add',
                image_list: [],
                form: {
                    id: '',
                    name: '',
                    college: '',
                    phone: '',
                    email: '',
                    office: '',
                    headImage: ''
                },
                param: new FormData(),
                rules: {
                    name: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'},
                        {min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur'}
                    ],
                    college: [
                        {required: true, message: '请选择学院', trigger: 'change'}
                    ],
                    phone: [
                        {required: true, message: '请输入电话号码', trigger: 'change'}
                    ],
                    email: [
                        {required: true, message: '请输入邮箱', trigger: 'change'}
                    ],
                    office: [
                        { /*type: 'array',*/ required: true, message: '请输入办公室地址', trigger: 'change'}
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
            this.param.append('deleteOldImage', 'false')//是否删除旧图片
        },
        methods: {
            setUpdate(id) {
                this.getById(id)
                this.operation = 'update';
                this.btn_text = '修改'
            },
            getById(id) {
                this.$axios.get( 'teacher/' + id).then((res) => {
                    console.log("getById", res)
                    let teacher = res.data.data;
                    this.form = teacher;
                    if (teacher.headImage != null && teacher.headImage != '') {
                        this.image_list.push({
                            name: this.form.headImage,
                            url: this.form.headImage
                        })
                    }
                })
            },
            beforeUpload(file) {
                console.log('beforeUpload', file)
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            handleChange(file, fileList) {
                console.log("handleChange", file)
                console.log("handleChange", fileList)
                let type = file.raw.type;
                let size = file.raw.size;
                if (!(type == 'image/jpg' || type == 'image/jpeg' || type == 'image/png')) {
                    fileList.splice(0, 1)
                    this.$message.error("图片格式必须为jpg，jpeg，png")
                    return;
                }
                if (size / 1024 / 1000 > 1) {
                    fileList.splice(0, 1)
                    this.$message.error("图片大小不能超过1m")
                }
                this.param.append('file', file.raw);
            },
            handleRemove(file, fileList) {
                this.param.delete('file')
                this.param.set('deleteOldImage', 'true')
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        for (let key in this.form) {
                            this.param.append(key, this.form[key])
                        }
                        if (!this.param.has('file')) {
                            this.param.append('file', new File([], ""))//添加一个空file不然接口会报错
                        }
                        if (this.operation == 'update') {
                            this.$axios.put( 'teacher/', this.param, config).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('teacher_list')
                            })
                        } else if (this.operation == 'add') {
                            this.$axios.post( 'teacher/', this.param, config).then((res) => {
                                this.$message({
                                    message: res.data.info,
                                    type: 'success'
                                })
                                this.$router.push('teacher_list')
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
