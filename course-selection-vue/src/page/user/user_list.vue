<template>
  <div class="container">
    <div style="margin-bottom: 5px;float: right ">
      <span>年级：</span>
      <el-select v-model="listParams.grade" slot="prepend" placeholder="请选择" @change="getList" style="width: 120px">
        <el-option label="不限年级" value=""></el-option>
        <el-option v-for="grade in this.$global.getGrades(30)" :label="grade" :value="grade"
                   :key="grade"></el-option>
      </el-select>
      <el-select v-model="listParams.college" slot="prepend" placeholder="请选择" @change="getList" style="width: 300px">
        <el-option label="不限学院" value=""></el-option>
        <el-option v-for="college in this.$global.colleges" :label="college" :value="college"
                   :key="college"></el-option>
      </el-select>
      <el-input placeholder="请输入内容" v-model="listParams.name" style="width: 300px">
        <el-button slot="append" icon="el-icon-search" @click="getList"></el-button>
      </el-input>
    </div>

    <el-table
      :data="user_list"
      border
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        width="100"
        label="微信头像"
        prop="headImage">
        <template slot-scope="scope">
          <div class="block">
            <el-avatar icon="el-icon-user-solid" shape="circle " fit="cover" :size="50" :src="scope.row.avatarUrl"></el-avatar>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label=姓名
        width="100">
      </el-table-column>
      <el-table-column
        prop="studentId"
        label="学号">
      </el-table-column>
      <el-table-column
        prop="college"
        label="学院">
      </el-table-column>
      <el-table-column
        prop="grade"
        label="年级">
      </el-table-column>
      <el-table-column
        prop="score"
        label="选课积分">
      </el-table-column>
      <el-table-column label="操作">
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
        name: "user_list",
        data() {
            return {
                listParams:{
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    college:'',
                    name:'',
                    grade:'',
                },
                user_list: [],
                total: 0,//数据总量
                pageCount: 0,//总页数
            }
        },
        created: function () {
            this.getList();
        },
        methods: {
            handleEdit(id) {
                this.$router.push({
                    path: 'user_add',
                    query: {id: id}
                })
            },
            handleDelete(id) {
                this.delete(id)
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
            delete(id) {
                this.$axios.delete( 'user/' + id).then((res) => {
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
                this.$axios.post( 'user/list', this.listParams).then((res) => {
                    console.log(res)
                    this.user_list = res.data.data
                    this.total = res.data.total
                    this.pageCount = res.pageCount
                })
            }
        }
    }
</script>

<style scoped>
  @import '../../style/common.css';
</style>
