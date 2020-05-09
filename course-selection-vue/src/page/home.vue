<template>
  <div class="fullContainer">
    <el-row :gutter="0" style="height: 100%;">
      <el-col :span="4" style="min-height: 100%;background-color: #324057;">
        <el-menu
          theme="dark"
          default-active=""
          :default-active="this.$route.path.substr(1)"
          @open="handleOpen"
          @close="handleClose"
          background-color="#324057"
          text-color="#fff"
          active-text-color="#ffd04b"
          router>
          <el-menu-item index="index">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>首页</span>
            </template>
          </el-menu-item>

          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>选课管理</span>
            </template>
            <el-menu-item index="item_list">选课列表</el-menu-item>
            <el-menu-item index="item_add">添加选课</el-menu-item>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>课程管理</span>
            </template>
            <el-menu-item index="course_list">备选课程</el-menu-item>
            <el-menu-item index="course_add">添加课程</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>基础课程管理</span>
            </template>
            <el-menu-item index="baseCourse_list">基础课程</el-menu-item>
            <el-menu-item index="baseCourse_add">添加课程</el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>教师管理</span>
            </template>
            <el-menu-item index="teacher_list">教师列表</el-menu-item>
            <el-menu-item index="teacher_add">添加教师</el-menu-item>
          </el-submenu>

          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>教室管理</span>
            </template>
            <el-menu-item index="classroom_list">教室列表</el-menu-item>
            <el-menu-item index="classroom_add">添加教室</el-menu-item>
          </el-submenu>

          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>用户管理</span>
            </template>
            <el-menu-item index="user_list">用户列表</el-menu-item>
            <el-menu-item index="user_add">添加用户</el-menu-item>
          </el-submenu>

          <el-menu-item index="setting">
            <template slot="title">
              <i class="el-icon-setting"></i>评教设置
            </template>
          </el-menu-item>

          <el-menu-item index="about">
            <template slot="title">
              <i class="el-icon-warning"></i>关于
            </template>
          </el-menu-item>

        </el-menu>
      </el-col>

      <el-col :span="20" style="height: 100%;overflow: auto;">
        <el-menu
          default-active="1"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#324057"
          text-color="#fff"
          active-text-color="#ffd04b"
          router>
          <el-menu-item @click="returnLastPage" >
            <template slot="title">
              <i class="el-icon-back"></i>
              <span>返回</span>
            </template>
          </el-menu-item>
          <el-menu-item index="" style="color: rgb(255,197,82)">{{userName}}</el-menu-item>
          <el-submenu index="2">
            <template slot="title">选项</template>
            <el-menu-item index="login">退出登录</el-menu-item>
          </el-submenu>
        </el-menu>

        <!-- 变化的页面位置-->
        <router-view/>
      </el-col>
    </el-row>
  </div>
</template>

<script>

    import router from "../router";

    export default {
        name: "home",
        data() {
            return {
                userName: localStorage.getItem('userName')
            }
        },
        methods: {
            returnLastPage(){
                router.back()
            },
            handleOpen(key, keyPath) {
                //console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                //console.log(key, keyPath);
            },
            open(path) {
                this.$router.push(path)
            },
            handleSelect(key, keyPath) {
                //console.log(key, keyPath);
            }
        },
        computed: {},
    }
</script>

<style scoped>

</style>
