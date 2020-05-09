<template>
  <div class="container">
    <div><span>当前选中：<span v-if="this.targetClassroom==null">无</span></span>
      <span v-if="this.targetClassroom!=null">
        {{this.targetClassroom.name}}
        <el-button type="success" @click="confirmClassroom">确定</el-button>
      </span>
    </div>

    <div style="margin-bottom: 5px;margin-right: 10px;float: right ">
      <span style="margin-right: 10px;">
        <el-select v-model="sizeSelect" slot="prepend" placeholder="请选择" @change="sizeChange" style="width: 150px">
          <el-option label="不限教室规模" value=""></el-option>
          <el-option label="小型（60以下）" value="small"></el-option>
          <el-option label="中型（60-120）" value="middle"></el-option>
          <el-option label="大型（120-300）" value="big"></el-option>
          <el-option label="超大型（300以上）" value="superBig"></el-option>
        </el-select>
      </span>

      <span style="margin-right: 10px;">
        <el-select v-model="listParams.building" slot="prepend" placeholder="请选择"
                   style="width: 150px" @change="buildingChange">
          <el-option label="不限教学楼" value=""></el-option>
          <el-option v-for="building in this.$global.buildings.concat(this.$global.colleges)" :label="building"
                     :value="building"
                     :key="building"></el-option>
        </el-select>
      </span>

      <span style="margin-right: 10px;">
        <el-select v-model="listParams.floor" slot="prepend" placeholder="请选择" style="width: 100px"
                   @change="floorChange">
          <el-option label="不限楼层" value="0"></el-option>
          <el-option v-for="floor in 10" :label="floor" :value="floor"
                     :key="floor"></el-option>
        </el-select>
      </span>

    </div>

    <el-table
      :data="classroom_list"
      border
      @current-change="handleRowCurrentChange"
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="name"
        label=识别号
        width="150">
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
        name: "classroom_select",
        props: {
            time: {},
            open: false
        },
        watch: {
            open(val){
                if (val){
                    this.getList()
                }
            },
            /*time: {//深度监听，可监听到对象、数组的变化
                handler(val, oldVal) {
                    console.log("b.c: " + val.year);
                },
                deep:true
            }*/
        },
        data() {
            return {
                listParams: {
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    floor: "0",
                    building: '',
                    leftSize: 0,
                    rightSize: 0,

                    classIndex: 0,
                    weekDay: 0,
                    startWeek: 0,
                    endWeek: 0,
                    year: 0,
                    quarter: ''
                },
                classroom_list: [],
                total: 0,

                pageCount: 0,
                floorSelect: '',
                buildingSelect: '',
                sizeSelect: '',
                targetClassroom: null
            }
        },
        created: function () {
            this.getList();
        },
        methods: {
            confirmClassroom() {
                console.log(this.time)
                this.$emit('getClassroom', this.targetClassroom)
            },
            handleRowCurrentChange(val) {
                this.targetClassroom = val;
            },
            sizeChange() {
                if (this.sizeSelect == 'small') {
                    this.listParams.rightSize = 60;
                } else if (this.sizeSelect == 'middle') {
                    this.listParams.leftSize = 60;
                    this.listParams.rightSize = 120;
                } else if (this.sizeSelect == 'big') {
                    this.listParams.leftSize = 120;
                    this.listParams.rightSize = 300;
                } else if (this.sizeSelect == 'superBig') {
                    this.listParams.leftSize = 300;
                } else {
                    this.listParams.leftSize = 0;
                    this.listParams.rightSize = 0;
                }
                this.getList()
            },
            buildingChange() {
                this.getList()
            },
            floorChange() {
                this.getList()
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
            //time参数过多，复制部分参数给listParams
            prepareData(){
                this.listParams.year = this.time.year
                this.listParams.quarter = this.time.quarter
                this.listParams.startWeek = this.time.startWeek
                this.listParams.endWeek = this.time.endWeek
                this.listParams.weekDay = this.time.weekDay
                this.listParams.classIndex = this.time.classIndex
            },
            getList() {
                this.prepareData();
                this.$axios.post(this.$global.host + 'classroom/listEnable', this.listParams).then((res) => {
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
  @import '../style/common.css';
</style>
