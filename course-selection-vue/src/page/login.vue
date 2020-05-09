<template>
  <div class="body">
    <br><br><br><br><br>
    <el-card class="box-card" >
      <h2 style="color: #595959">SCAU选课系统</h2>
      <el-form :model="form" :rules="rules" ref="myForm" label-width="60px" class="demo-ruleForm">
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-button type="success" @click="login('myForm')" style="width: 200px">登录</el-button>
        <el-button type="" @click="reset('myForm')">重置</el-button>
      </el-form>
    </el-card>

  </div>
</template>

<script>
    export default {
        name: "login",
        data() {
            return {
                form: {
                    account: '',
                    password: ''
                },
                rules: {
                    account: [
                        {required: true, message: '请输入账号', trigger: 'change'}
                    ],
                    password: [
                        {required: true, message: '请选择密码', trigger: 'change'}
                    ]
                }
            }
        },
        methods: {
            login(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$axios.post( 'admin/login/', this.form).then((res) => {
                            if (res.data.code == 0) {
                                //保存token
                                localStorage.setItem("token", res.data.data.token)
                                localStorage.setItem("userName",res.data.data.name)
                                this.$router.replace('home')
                            }
                            this.$message.success(res.data.info);
                        })
                    }
                })
            },
            //重置输入框
            reset(form) {
                this.$refs[form].resetFields()
            }
        }
    }
</script>

<style scoped>
  .body {
    width: 100%;
    height:  100%;
    background-image: url("../pic/b.jpg");
  }

  .box-card {
    text-align: center;
    width: 450px;
    margin: 0 auto;
  }
</style>
