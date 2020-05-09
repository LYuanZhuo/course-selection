<template>
  <div class="container">
    <div style="margin-bottom: 5px;margin-right: 10px;float: right ">
      <div v-if="this.targetBasecourse!=null">
        <span>当前选中：</span>{{this.targetBasecourse.name}}
        <el-button type="success" @click="confirmBasecourse">确定</el-button>
      </div>
      <el-select v-model="listParams.college" slot="prepend" placeholder="请选择" @change="getList"
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
      <el-input placeholder="请输入内容" v-model="searchValue" style="width: 300px">
        <el-select v-model="searchKey" slot="prepend" placeholder="关键字"  style="width: 100px">
          <el-option label="课程名" value="name"></el-option>
          <el-option label="教师" value="teacherName"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>
    </div>

    <el-table
      :data="basecourse_list"
      border
      style="width: 100%"
      @current-change="handleRowCurrentChange"
    >
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="name"
        label=课程名
        width="200">
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型">
      </el-table-column>
      <el-table-column
        prop="detail"
        label="介绍">
      </el-table-column>
      <el-table-column
        prop="college"
        label="学院">
      </el-table-column>
      <el-table-column
        prop="teacherName"
        label="教师">
      </el-table-column>
      <el-table-column
        prop="score"
        label="评分">
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
        name: "basecourse_select",
        data() {
            return {
                listParams: {
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    name: '',
                    type:'',
                    teacherName: '',
                    college: ''
                },
                basecourse_list: [],
                total: 0,
                pageCount: 0,
                searchKey: 'name',
                searchValue: '',
                targetBasecourse: null
            }
        },
        created: function () {
            this.getList();
        },
        methods: {
            confirmBasecourse() {
                this.$emit('getBasecourse', this.targetBasecourse)
            },
            handleRowCurrentChange(val) {
                this.targetBasecourse = val;
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
            delete(id) {
                this.$axios.delete(this.$global.host + 'basecourse/' + id).then((res) => {
                    console.log(res)
                    this.getList()
                    this.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                })
            },
            getList() {
                this.$axios.post(this.$global.host + 'basecourse/list', this.listParams).then((res) => {
                    console.log(res)
                    this.basecourse_list = res.data.data;
                    this.total = res.data.total;
                    this.pageCount = res.pageCount;
                })
            }
        }
    }
</script>

<style scoped>
  @import '../style/common.css';
</style>
