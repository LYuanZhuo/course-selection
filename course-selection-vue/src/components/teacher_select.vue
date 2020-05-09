<template>
  <div class="container">

    <div style="margin-bottom: 5px;float: right ">
      <div v-if="this.targetTeacher!=null">
        <span>当前选中：</span>{{this.targetTeacher.name}}
        <el-button type="success" @click="confirmTeacher">确定</el-button>
      </div>
      <el-select v-model="college" slot="prepend" placeholder="请选择"  style="width: 200px">
        <el-option :label="college" :value="college"></el-option>
      </el-select>
      <el-input placeholder="请输入内容" v-model="searchValue" style="width: 300px">
        <el-select v-model="searchKey" slot="prepend" placeholder="请选择" style="width: 80px">
          <el-option label="姓名" value="name"></el-option>
          <el-option label="电话" value="phone"></el-option>
          <el-option label="邮箱" value="email"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>
    </div>

    <el-table
      highlight-current-row
      @current-change="handleRowCurrentChange"
      :data="teacher_list"
      border
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        width="100"
        label="照片"
        prop="headImage">
        <template slot-scope="scope">
          <div class="block">
            <el-avatar icon="el-icon-user-solid" shape="square" :size="50" :src="scope.row.headImage"></el-avatar>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label=姓名
        width="100">
      </el-table-column>
      <el-table-column
        prop="phone"
        label="电话">
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱">
      </el-table-column>
      <el-table-column
        prop="college"
        label="学院">
      </el-table-column>
      <el-table-column
        prop="office"
        label="办公室">
      </el-table-column>
    </el-table>
    <div style="float: right;margin: 10px;">
      <el-pagination
        @size-change="handlePageSizeChange"
        @current-change="handlePageCurrentChange"
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
        name: "teacher_select",
        props: {
            open: false,
            college: ''
        },
        watch:{
          open(val){
              if (val){
                  this.getList()
              }
          }
        },
        data() {
            return {
                listParams:{
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: '',
                    ascOrDesc: -1,
                    college:''
                },
                teacher_list: [],
                total: 0,//数据总量
                pageCount: 0,//总页数
                collegeSelect: '',
                searchKey: 'name',
                searchValue: '',
                targetTeacher:null
            }
        },
        created: function () {
            this.getList();
        },
        methods: {
            confirmTeacher(){
                this.$emit('getTeacher',this.targetTeacher)
            },
            handleRowCurrentChange(val){
                this.targetTeacher = val;
            },
            collegeChange(){
                if (this.collegeSelect != '') {
                    this.listParams.conditions['college'] = this.collegeSelect
                }else{
                    delete this.listParams.conditions.college
                }
                this.getList()
            },
            search() {
                this.listParams.searches = {}
                if (this.searchValue != '') {
                    this.listParams.searches[this.searchKey] = this.searchValue
                }
                this.getList();
            },
            handlePageSizeChange(val) {
                this.listParams.pageSize = val
                this.listParams.currentPage = 1
                this.getList();
            },
            handlePageCurrentChange(val) {
                this.listParams.currentPage = val
                this.getList();
            },
            getList() {
                this.listParams.college = this.college
                this.$axios.post(this.$global.host + 'teacher/list', this.listParams).then((res) => {
                    console.log(res)
                    this.teacher_list = res.data.data
                    this.total = res.data.total
                    this.pageCount = res.pageCount
                })
            }
        }
    }
</script>

<style scoped>
  @import '../style/common.css';
</style>
