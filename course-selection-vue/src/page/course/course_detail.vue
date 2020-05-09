<template>
  <div class="container">
    <div class="courseDetailContainer">
      <div class="courseName">{{course.courseName}}</div>
      <div class="infoContainer">
        <div class="propertyContainer">
          <span class="propertyKey">课程类型：</span>
          <span class="propertyValue">{{course.type}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">教师：</span>
          <span class="propertyValue">{{course.teacherName}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">学院：</span>
          <span class="propertyValue">{{course.collegeName}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">所属项目：</span>
          <span class="propertyValue">{{course.itemName}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">教室：</span>
          <span class="propertyValue">{{course.classroomName}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">上课时间：</span>
          <span class="propertyValue">{{course.courseTimeDetail}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">容量：</span>
          <span class="propertyValue">{{course.count}}</span>
        </div>


      </div>
    </div>
    <div class="userListContainer">
      <div class="conditionListContainer">
        <span>排序：</span>
        <el-select size="small" v-model="listParams.orderBy" slot="prepend" placeholder="请选择" @change="getList"
                   style="width: 150px">
          <el-option label="按时间倒序" value="time"></el-option>
          <el-option label="按积分倒序" value="score"></el-option>
        </el-select>
        <span>选课状态：</span>
        <el-select size="small" v-model="listParams.status" slot="prepend" placeholder="请选择" @change="getList"
                   style="width: 150px">
          <el-option label="全部状态" value="-1"></el-option>
          <el-option label="选课成功" value="1"></el-option>
          <el-option label="选课失败" value="2"></el-option>
        </el-select>
      </div>
      <el-table :data="user_list" border style="width: 100%">
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column width="100" label="微信头像" prop="headImage">
          <template slot-scope="scope">
            <div class="block">
              <el-avatar icon="el-icon-user-solid" shape="circle " fit="cover" :size="50"
                         :src="scope.row.avatarUrl"></el-avatar>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="姓名"></el-table-column>
        <el-table-column prop="studentId" label="学号"></el-table-column>
        <el-table-column prop="college" label="学院"></el-table-column>
        <el-table-column prop="grade" label="年级"></el-table-column>
        <el-table-column prop="score" label="选课积分"></el-table-column>
        <el-table-column prop="time" label="选课时间"></el-table-column>
        <el-table-column prop="statusInfo" label="选课状态"></el-table-column>
      </el-table>
      <div style="float: right;margin: 10px;">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="listParams.currentPage"
          :page-count="pageCount"
          :page-sizes="[20, 40, 60]"
          :page-size="listParams.pageSize"
          layout="total,sizes, prev, pager, next"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "course_detail",
        data() {
            return {
                listParams: {
                    courseId: 0,
                    status: "-1",
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: "time",
                    ascOrDesc: "desc"
                },
                course: {},
                user_list: [],
                total: 0,
                pageCount: 0
            };
        },
        created() {
            this.listParams.courseId = this.$route.query.id;
            this.getList();
            this.getCourse()
        },
        methods: {
            handleSizeChange(val) {
                this.listParams.pageSize = val;
                this.listParams.currentPage = 1;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listParams.currentPage = val;
                this.getList();
            },
            getCourse() {
                this.$axios.get('course/' + this.listParams.courseId).then(res => {
                    if (res.data.code == 0) {
                        this.course = res.data.data
                    }
                })
            },
            getList() {
                this.$axios({
                    method: "post",
                    url: "user_course/listUsersByCourse",
                    data: this.listParams
                }).then(res => {
                    console.log(res);
                    this.user_list = res.data.data;
                    this.total = res.data.total;
                    this.pageCount = res.data.pageCount;
                });
            }
        }
    }
</script>

<style scoped>
  @import "../../style/common.css";

  .courseDetailContainer {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 20px;
    margin-bottom: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }

  .courseName {
    width: 15%;
    font-size: 20px;
    text-align: center;
    color: #696969;
    font-weight: bold;
  }

  .infoContainer {
    width: 85%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }

  .propertyContainer {
    width: 250px;
    font-size: 13px;
    margin-left: 10px;
    margin-bottom: 5px;
  }

  .propertyContainer .propertyKey {
    color: black;
  }

  .propertyContainer .propertyValue {
    color: #696969;
  }
  .conditionListContainer{
    margin-bottom: 5px;
  }
  .userListContainer{

  }
</style>
