<template>
  <div class="container">
    <div style="margin-bottom: 5px;margin-right: 10px;float: right ">

      <div style="float: right;margin-right: 10px;">
        <span style="color: dimgray;font-size: 15px">选择教室规模：</span>
        <el-select v-model="sizeSelect" slot="prepend" placeholder="请选择" @change="sizeChange"
                   style="width: 200px">
          <el-option label="不限" value=""></el-option>
          <el-option label="小型（60以下）" value="small"></el-option>
          <el-option label="中型（60-120）" value="middle"></el-option>
          <el-option label="大型（120-300）" value="big"></el-option>
          <el-option label="超大型（300以上）" value="superBig"></el-option>
        </el-select>
      </div>

      <div style="float: right;margin-right: 10px;">
        <span style="color: dimgray;font-size: 15px">选择教学楼：</span>
        <el-select v-model="listParams.building" slot="prepend" placeholder="请选择" @change="buildingChange"
                   style="width: 200px">
          <el-option label="不限" value=""></el-option>
          <el-option v-for="building in this.$global.buildings.concat(this.$global.colleges)" :label="building"
                     :value="building"
                     :key="building"></el-option>
        </el-select>
      </div>

      <div style="float: right;margin-right: 10px;">
        <span style="color: dimgray;font-size: 15px">选择楼层：</span>
        <el-select v-model="listParams.floor" slot="prepend" placeholder="请选择" @change="floorChange" style="width: 100px">
          <el-option label="不限" value="0"></el-option>
          <el-option v-for="floor in 10" :label="floor" :value="floor"
                     :key="floor"></el-option>
        </el-select>
      </div>

    </div>

    <el-table
      :data="classroom_list"
      border
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="name"
        label=识别号
        width="200">
      </el-table-column>
      <el-table-column
        prop="building"
        label="教学楼">
      </el-table-column>
      <el-table-column
        prop="floor"
        label="楼层">
      </el-table-column>
      <el-table-column
        prop="size"
        label="容纳人数">
      </el-table-column>
      <el-table-column
        prop="note"
        label="备注">
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
        name: "classroom_list",
        data() {
            return {
                listParams: {
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    floor:"0",
                    building:'',
                    leftSize: 0,
                    rightSize: 0
                },
                classroom_list: [],
                total: 0,
                pageCount: 0,
                sizeSelect:''
            }
        },
        created: function () {
            this.getList();
        },
        methods: {
            sizeChange(){
                if (this.sizeSelect=='small'){
                    this.listParams.rightSize = 60;
                }else if (this.sizeSelect=='middle'){
                    this.listParams.leftSize = 60;
                    this.listParams.rightSize = 120;
                }else if (this.sizeSelect=='big'){
                    this.listParams.leftSize = 120;
                    this.listParams.rightSize = 300;
                }else if (this.sizeSelect=='superBig'){
                    this.listParams.leftSize = 300;
                }else{
                    this.listParams.leftSize = 0;
                    this.listParams.rightSize = 0;
                }
                this.getList()
            },
            buildingChange(){
                this.getList()
            },
            floorChange(){
                this.getList()
            },
            handleEdit(id) {
                this.$router.push({
                    path: 'classroom_add',
                    query: {id: id}
                })
            },
            handleDelete(id) {
                this.delete(id)
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
                this.$axios.delete( 'classroom/' + id).then((res) => {
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
                this.$axios.post( 'classroom/list', this.listParams).then((res) => {
                    console.log(res)
                    this.classroom_list = res.data.data;
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
