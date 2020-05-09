<template>
  <div class="container">
    <div style="margin-bottom: 5px; ">
      <el-date-picker
        @change="getList"
        style="width: 120px"
        value-format="yyyy"
        v-model="listParams.year"
        type="year"
        placeholder="全部年份">
      </el-date-picker>
      <el-select v-model="listParams.quarter" placeholder="请选择季度" style="width: 120px" @change="getList">
        <el-option label="全部季度" value=""></el-option>
        <el-option v-for="item in [{value:'秋季'},{value: '春季'}]":key="item.value" :label="item.value" :value="item.value"></el-option>
      </el-select>
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
      :data="course_list"
      border
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="courseName"
        label=课程名
        min-width="100"
      >
        <template slot-scope="scope">
          <span style="cursor: pointer" @click="handleDetail(scope.row.id)">{{scope.row.courseName}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="itemName"
        label=所属项目
        min-width="100"
      >
        <template slot-scope="scope">
          {{scope.row.itemName ==null ? '未选定' : scope.row.itemName}}
        </template>
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
        min-width="160"
        prop="courseTimeDetail"
        label="上课时间">
      </el-table-column>
      <el-table-column
        prop="count"
        label="容量">
      </el-table-column>
      <el-table-column
        prop="selectCount"
        label="已选">
      </el-table-column>
      <el-table-column min-width="150" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.row.id)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
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
        :total="total">
      </el-pagination>

    </div>

  </div>
</template>

<script>
    export default {
        name: "course_list",
        data() {
            return {
                listParams: {
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    courseName: '',
                    teacherName: '',
                    collegeName: '',
                    type:'',
                    year: '',
                    quarter: '',
                },
                course_list: [],
                total: 0,
                pageCount: 0,
                searchKey: 'courseName',
                searchValue: ''
            }
        },
        created() {
            this.getList();
            this.$global.setNow(this.listParams)
        },
        methods: {
            handleDetail(id) {
                this.$router.push({
                    path: 'course_detail',
                    query: {id: id}
                })
            },
            handleEdit(id) {
                this.$router.push({
                    path: 'course_add',
                    query: {id: id}
                })
            },
            handleDelete(id) {
                this.delete(id)
            },
            search() {
                this.listParams.teacherName = '';
                this.listParams.courseName = '';
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
            delete(id) {
                this.$axios.delete('course/' + id).then((res) => {
                    console.log(res)
                    this.getList()
                    if (res.data.code==0){
                        this.$message.success(res.data.info)
                    }else {
                        this.$message.error(res.data.info)
                    }
                })
            },
            getList() {
                this.$axios({
                    method: 'post',
                    url: 'course/list',
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
  @import '../../style/common.css';
</style>
