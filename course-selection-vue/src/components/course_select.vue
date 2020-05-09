<template>
  <div class="container">
    <div style="margin-bottom: 5px;margin-right: 10px;float: right ">
      <el-select v-model="listParams.collegeName" slot="prepend" placeholder="请选择" @change="getList"
                 style="width: 200px">
        <el-option label="不限学院" value=""></el-option>
        <el-option v-for="college in this.$global.colleges" :label="college" :value="college"
                   :key="college"></el-option>
      </el-select>
      <el-select v-model="listParams.type" slot="prepend" placeholder="请选择" @change="getList"
                 style="width: 150px">
        <el-option label="不限类型" value=""></el-option>
        <el-option label="公共选修" value="公共选修" key="公共选修"></el-option>
        <el-option label="A系列" value="A系列" key="A系列"></el-option>
        <el-option label="专业选修" value="专业选修" key="专业选修"></el-option>
      </el-select>
      <el-input placeholder="请输入内容" v-model="searchValue" style="width: 400px">
        <el-select v-model="searchKey" slot="prepend" placeholder="关键字"  style="width: 120px">
          <el-option label="课程名" value="courseName"></el-option>
          <el-option label="教师" value="teacherName"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>
    </div>
    <el-table
      @selection-change="courseSelect"
      :data="course_list"
      border
      style="width: 100%">
      <el-table-column
        type="selection"
        width="50">
      </el-table-column>
      <el-table-column
        prop="courseName"
        label=课程名
        min-width="100"
      >
      </el-table-column>
      <el-table-column
        prop="type"
        label=类型
        min-width="60">
      </el-table-column>
      <el-table-column
        prop="collegeName"
        label="学院">
      </el-table-column>
      <el-table-column
        prop="teacherName"
        label="教师">
      </el-table-column>
      <el-table-column
        prop="classroomName"
        label="教室"
        min-width="100">
      </el-table-column>
      <el-table-column
        prop="year"
        label="学年">
      </el-table-column>
      <el-table-column
        prop="quarter"
        label="季度">
      </el-table-column>
      <el-table-column
        prop=""
        label="时间">
        <template slot-scope="scope">
          <span>{{ scope.row.startWeek }}-{{ scope.row.endWeek }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="count"
        label="容量">
      </el-table-column>
      <el-table-column
        prop="selectCount"
        label="已选">
      </el-table-column>
    </el-table>
    <div style="float: right;margin: 10px;">
      <span><el-button @click="add">确认添加</el-button></span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="listParams.currentPage"
        :page-count="pageCount"
        :page-sizes="[20, 40, 60]"
        :page-size="listParams.pageSize"
        layout="total,sizes, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
    export default {
        name: "course_select",
        props: {
            year: 0,
            quarter: '',
            open: false,
            itemId: 0
        },
        watch: {
            open(val) {
                if (val) {
                    this.getList()
                }
            }
        },
        data() {
            return {
                listParams: {
                    year: 0,
                    quarter: '',
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    courseName: '',
                    teacherName: '',
                    collegeName: '',
                    type:''
                },
                batchAddParams: {
                    itemId: 0,
                    courseIds: []
                },
                selectCourseIds: [],
                course_list: [],
                total: 0,
                pageCount: 0,
                searchKey: 'courseName',
                searchValue: ''
            }
        },
        created: function () {
            this.getList();
        },
        methods: {
            confirm(){
              this.$emit("confirm")
            },
            add() {
                this.batchAddParams.itemId = this.itemId
                this.batchAddParams.courseIds = this.selectCourseIds
                this.$axios.post('course/batchAddCourseToItem',this.batchAddParams).then((res)=>{
                    if (res.data.code==0){
                        this.$message.success(res.data.info)
                    }else{
                        this.$message.error(res.data.info)
                    }
                    this.confirm()
                })
            },
            courseSelect(val) {
                this.selectCourseIds = []
                val.forEach((course) => {
                    this.selectCourseIds.push(course.id)
                })
            },
            search() {
                this.listParams.teacherName = '';
                this.listParams.name = '';
                this.listParams[this.searchKey] = this.searchValue;
                this.getList();
            },
            handleSizeChange(val) {
                this.listParams.pageSize = val;
                this.listParams.currentPage = 1;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listParams.currentPage = val;
                this.getList();
            },
            getList() {
                this.listParams.year = this.year;
                this.listParams.quarter = this.quarter;
                this.$axios({
                    method: 'post',
                    url: 'course/listEnableByItemId',
                    data: this.listParams
                }).then((res) => {
                    console.log(res)
                    this.course_list = res.data.data;
                    this.total = res.data.total;
                    this.pageCount = res.data.pageCount;
                })
            }
        }
    }
</script>

<style scoped>
  @import '../style/common.css';
</style>
