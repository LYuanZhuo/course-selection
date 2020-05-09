<template>
  <div class="container">
    <div style="margin-bottom: 5px;">
      <span style="margin-right: 10px">
        <el-date-picker
          @change="getList"
          style="width: 120px"
          value-format="yyyy"
          v-model="listParams.year"
          type="year"
          placeholder="全部年份">
        </el-date-picker>

        <el-select v-model="listParams.quarter" placeholder="请选择" style="width: 100px" @change="getList">
          <el-option label="不限" value=""></el-option>
          <el-option v-for="item in [{value:'秋季'},{value: '春季'}]" :key="item.value" :label="item.value" :value="item.value"></el-option>
        </el-select>
      </span>
      <span style="margin-right: 10px">
        <el-input placeholder="请输入选课名" v-model="listParams.name" style="width: 300px">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
      </span>
    </div>

    <el-table
      :data="item_list"
      border
      style="width: 100%">
      <el-table-column
        label="启用"
        min-width="40">
        <template slot-scope="scope">
          <el-switch
            @change="enableItem(scope.row.id)"
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="gray">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="项目名">
        <template slot-scope="scope">
          <span style="cursor: pointer" @click="handleDetail(scope.row.id)">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="detail"
        label="介绍">
      </el-table-column>
      <el-table-column
        prop="grades"
        label="选课年级">
      </el-table-column>
      <el-table-column
        prop="maxSelectNum"
        label="最大选课数量">
      </el-table-column>
      <el-table-column
        prop="startTime"
        label="开始时间">
      </el-table-column>
      <el-table-column
        prop="endTime"
        label="结束时间">
      </el-table-column>
      <el-table-column label="操作" width="150">
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
        name: "item_list",
        data() {
            return {
                listParams: {
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'start_time',
                    ascOrDesc: 'desc',
                    name: '',
                    year:'2020',
                    quarter:'秋',
                    startTime: '',
                    endTime: ''
                },
                item_list: [],
                total: 0,
                pageCount: 0,
                time: []
            }
        },
        created: function () {
            this.getList();
            this.$global.setNow(this.listParams)
        },
        methods: {
            //项目启用关闭
            enableItem(id){
                this.$axios.put("item/enable/"+id).then(res=>{
                    if (res.data.code==0){
                        this.$message.success(res.data.info)
                    }else {
                        this.$message.error(res.data.info)
                    }
                })
            },
            timeSelect() {
                if (this.time == null) {
                    this.listParams.startTime = '';
                    this.listParams.endTime = '';
                } else {
                    this.listParams.startTime = this.time[0];
                    this.listParams.endTime = this.time[1];
                }
                this.getList()
            },
            collegeChange() {
                if (this.collegeSelect != '') {
                    this.listParams.conditions['college'] = this.collegeSelect
                } else {
                    delete this.listParams.conditions['college']
                }
                this.getList()
            },
            handleDetail(id) {
                this.$router.push({
                    path: 'item_detail',
                    query: {id: id}
                })
            },
            handleDelete(id){
              this.delete(id)
            },
            handleEdit(id) {
                this.$router.push({
                    path: 'item_add',
                    query: {id: id}
                })
            },
            search() {
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
                this.$axios.delete('item/' + id).then((res) => {
                    this.getList()
                    if (res.data.code==0){
                        this.$message.success(res.data.info)
                    }else {
                        this.$message.error(res.data.info)
                    }
                })
            },
            getList() {
                this.$axios.post('item/list', this.listParams).then((res) => {
                    console.log(res)
                    this.item_list = res.data.data;
                    this.total = res.data.total;
                    this.pageCount = res.pageCount;
                })
            }
        }
    }
</script>

<style scoped>
  @import '../../style/common.css';
</style>
