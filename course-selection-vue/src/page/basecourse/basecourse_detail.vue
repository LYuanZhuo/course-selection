<template>
  <div class="container">
    <div class="courseDetailContainer">
      <div class="courseName">
        <div>{{basecourse.name}}</div>
        <el-rate v-model="basecourse.score" disabled  text-color="#ff9900" ></el-rate>
      </div>
      <div class="infoContainer">
        <div class="propertyContainer">
          <span class="propertyKey">课程类型：</span>
          <span class="propertyValue">{{basecourse.type}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">学院：</span>
          <span class="propertyValue">{{basecourse.college}}</span>
        </div>
        <div class="propertyContainer">
          <span class="propertyKey">教师：</span>
          <span class="propertyValue">{{basecourse.teacherName}}</span>
        </div>

      </div>
    </div>
    <div class="commentListContainer">
      <div class="conditionListContainer">
        <span>评价类型：</span>
        <el-select size="small" v-model="listParams.grade" slot="prepend" placeholder="请选择" @change="getList"
                   style="width: 150px">
          <el-option label="全部评价" value="0"></el-option>
          <el-option label="好评" value="1"></el-option>
          <el-option label="中评" value="2"></el-option>
          <el-option label="差评" value="3"></el-option>
        </el-select>
      </div>
      <el-table :data="comment_list" border style="width: 100%">
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column width="100" label="微信头像" prop="headImage">
          <template slot-scope="scope">
            <el-avatar icon="el-icon-user-solid" shape="circle " fit="cover" :size="50" :src="scope.row.userAvatarUrl"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="姓名"></el-table-column>
        <el-table-column prop="userName" label="评分" min-width="90">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.score" disabled show-score text-color="#ff9900" ></el-rate>
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评价"></el-table-column>
        <el-table-column prop="callName" label="点名情况"></el-table-column>
        <el-table-column prop="homework" label="作业情况"></el-table-column>
        <el-table-column prop="time" label="点评时间"></el-table-column>
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
        name: "basecourse_detail",
        data() {
            return {
                listParams: {
                    basecourseId: 0,
                    grade:'0',
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: "time",
                    ascOrDesc: "desc"
                },
                basecourse: {},
                comment_list: [],
                total: 0,
                pageCount: 0
            };
        },
        created() {
            this.listParams.basecourseId = this.$route.query.id;
            this.getList();
            this.getBaseCourse()
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
            getBaseCourse() {
                this.$axios.get('basecourse/' + this.listParams.basecourseId).then(res => {
                    if (res.data.code == 0) {
                        this.basecourse = res.data.data
                    }
                })
            },
            getList() {
                this.$axios({
                    method: "post",
                    url: "comment/listByCourse",
                    data: this.listParams
                }).then(res => {
                    console.log(res);
                    this.comment_list = res.data.data;
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
    width: 20%;
    font-size: 20px;
    text-align: center;
    color: #696969;
    font-weight: bold;
  }

  .infoContainer {
    width: 80%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }

  .propertyContainer {
    width: 220px;
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
    margin: 10px;
  }
  .commentListContainer{

  }
</style>
