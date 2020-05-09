<template>

  <div style="padding-left: 15px;padding-top: 10px">
    <el-dialog
      title="提示"
      :visible.sync="removeDialogVisible"
      width="20%"
      :before-close="handleClose">
      <span>确定移除此课程？</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="removeDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="confirmRemove">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog title="课程选择" :visible.sync="course_dialogTableVisible" width="1000px">
      <course_select @confirm="confirm" :itemId="itemId" :year="listParams.year" :quarter="listParams.quarter"
                     :open="course_dialogTableVisible"></course_select>
    </el-dialog>

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="项目详情" name="detail">
        <div class="courseDetailContainer">
          <div class="itemName">{{item.name}}</div>
          <div class="infoAndTimeContainer">
            <div class="infoContainer">
              <div class="propertyContainer">
                <span class="propertyKey">学期：</span>
                <span class="propertyValue">{{item.year}}{{item.quarter}}</span>
              </div>
              <div class="propertyContainer">
                <span class="propertyKey">年级：</span>
                <span class="propertyValue">{{item.grades}}</span>
              </div>
              <div class="propertyContainer">
                <span class="propertyKey">最大选课数量：</span>
                <span class="propertyValue">{{item.maxSelectNum}}</span>
              </div>
              <div class="propertyContainer">
                <span class="propertyKey">启用状态：</span>
                <span class="propertyValue">{{item.enable?'已启用':'未启用'}}</span>
              </div>
            </div>
            <div class="timeContainer">
              <span class="propertyKey"><i class="el-icon-timer"></i>选课时间：</span>
              <span class="propertyValue">{{item.startTime}}  -  {{item.endTime}}</span>
            </div>
          </div>
        </div>
        <div style="margin-bottom: 5px;">
          <span><el-button icon="el-icon-circle-plus" @click="selectCourse">新增课程</el-button></span>
          <el-select v-model="listParams.collegeName" slot="prepend" placeholder="请选择" @change="collegeChange"
                     style="width: 200px">
            <el-option label="不限学院" value=""></el-option>
            <el-option v-for="college in this.$global.colleges" :label="college" :value="college"
                       :key="college"></el-option>
          </el-select>
          <el-select v-model="listParams.type" slot="prepend" placeholder="请选择" @change="findCourses"
                     style="width: 150px">
            <el-option label="不限类型" value=""></el-option>
            <el-option label="公共选修" value="公共选修" key="公共选修"></el-option>
            <el-option label="A系列" value="A系列" key="A系列"></el-option>
            <el-option label="专业选修" value="专业选修" key="专业选修"></el-option>
          </el-select>
          <el-input placeholder="请输入内容" v-model="searchValue" style="width: 400px">
            <el-select v-model="searchKey" slot="prepend" placeholder="关键字" style="width: 120px">
              <el-option label="课程名" value="courseName"></el-option>
              <el-option label="教师" value="teacherName"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </div>
        <el-table :data="course_list" border style="width: 100%">
          <el-table-column width="50">
            <template slot-scope="scope">
              <i class="el-icon-delete-solid deleteStyle" @click="openRemove(scope.row.id)"></i>
            </template>
          </el-table-column>
          <el-table-column prop="courseName" label=课程名 min-width="100">
            <template slot-scope="scope">
              <span style="cursor: pointer" @click="handleCourseDetail(scope.row.id)">{{scope.row.courseName}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="type" label=类型 min-width="60"></el-table-column>
          <el-table-column prop="collegeName" label="学院"></el-table-column>
          <el-table-column prop="teacherName" label="教师"></el-table-column>
          <el-table-column prop="classroomName" label="教室" min-width="100"></el-table-column>
          <el-table-column prop="year" label="学年"></el-table-column>
          <el-table-column prop="quarter" label="季度">
          </el-table-column>
          <el-table-column prop="" label="时间">
            <template slot-scope="scope">
              <span>{{ scope.row.startWeek }}-{{ scope.row.endWeek }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="count" label="容量"></el-table-column>
          <el-table-column prop="selectCount" label="已选"></el-table-column>
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
      </el-tab-pane>


      <el-tab-pane label="学院设置" name="college">
        <el-transfer
          :titles="titles"
          filterable
          filter-placeholder="请输入学院关键字"
          v-model="itemColleges.colleges"
          :data="totalColleges">
        </el-transfer>
        <el-button @click="updateItemCollege" icon="el-icon-success">确认修改</el-button>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    import course_select from "../../components/course_select";

    export default {
        name: "item_detail",
        components: {course_select},
        data() {
            return {
                activeName: 'detail',
                titles: ['可选学院', '已选学院'],
                course_dialogTableVisible: false,
                itemId: 0,
                listParams: {
                    itemId: 0,
                    year: 0,
                    quarter: '',
                    type: '',
                    pageSize: 20,
                    currentPage: 1,
                    orderBy: 'id',
                    ascOrDesc: 'desc',
                    courseName: '',
                    teacherName: '',
                    collegeName: ''
                },
                course_list: [],
                total: 0,
                pageCount: 0,
                searchKey: 'courseName',
                searchValue: '',
                item: {},
                totalColleges: [],
                itemColleges: {
                    colleges: [],
                    itemId: 0
                },
                removeDialogVisible: false,
                removeCourseId: 0
            };
        },
        created() {
            this.itemId = this.$route.query.id
            this.itemColleges.itemId = this.$route.query.id
            this.listParams.itemId = this.$route.query.id
            this.getColleges()
            this.getById(this.itemId)
            this.findCourses()
        },
        methods: {
            handleCourseDetail(id) {
                this.$router.push({
                    path: 'course_detail',
                    query: {id: id}
                })
            },
            getColleges() {
                let colleges = this.$global.colleges
                for (let i = 0; i < colleges.length; i++) {
                    this.totalColleges.push({key: colleges[i], label: colleges[i], value: colleges[i]})
                }
            },
            //打开删除确认框
            openRemove(courseId) {
                this.removeDialogVisible = true
                this.removeCourseId = courseId
            },
            confirmRemove() {
                this.removeDialogVisible = false
                this.removeCourse(this.removeCourseId)
            },
            removeCourse(courseId) {
                this.$axios.put("course/removeCourseFromItem/" + courseId).then((res) => {
                    if (res.data.code == 0) {
                        this.$message.success(res.data.info)
                    } else {
                        this.$message.error(res.data.info)
                    }
                    this.findCourses()
                })
            },
            //新增完课程回调
            confirm() {
                this.findCourses()
                this.course_dialogTableVisible = false
            },
            selectCourse() {
                this.course_dialogTableVisible = true
            },
            //切换标签
            handleClick(tab) {
                if (tab.index == 0) {
                    this.findCourses()
                } else if (tab.index == 1) {
                    this.findColleges(this.itemId)
                }
            },
            //修改关联学院
            updateItemCollege() {
                this.$axios.put('item_college/updateItemCollege', this.itemColleges).then((res) => {
                    if (res.data.code != 0) {
                        this.$message.error(res.data.info);
                    } else {
                        this.$message.success(res.data.info);
                    }
                })
            },
            //获取项目详情
            getById(id) {
                this.$axios.get('item/' + id).then((res) => {
                    this.item = res.data.data;
                    this.listParams.year = res.data.data.year
                    this.listParams.quarter = res.data.data.quarter
                })
            },
            //查找项目关联学院
            findColleges(id) {
                this.$axios.get('item_college/listByItemId/' + id).then((res) => {
                    this.itemColleges.colleges = res.data.data
                })
            },
            //获取关联课程
            findCourses() {
                this.$axios.post('course/listByItemId', this.listParams).then((res) => {
                    this.course_list = res.data.data;
                    this.total = res.data.total;
                    this.pageCount = res.data.pageCount;
                })
            },
            //学院选择
            collegeChange() {
                this.findCourses()
            },
            //搜索课程
            search() {
                this.listParams.teacherName = '';
                this.listParams.name = '';
                this.listParams[this.searchKey] = this.searchValue;
                this.findCourses();
            },
            handleSizeChange(val) {
                this.listParams.pageSize = val;
                this.listParams.currentPage = 1;
                this.findCourses();
            },
            handleCurrentChange(val) {
                this.listParams.currentPage = val;
                this.findCourses();
            },
        }
    }
</script>

<style scoped>
  @import '../../style/common.css';

  .courseDetailContainer {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 20px;
    margin-bottom: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    color: #696969;
  }

  .itemName {
    width: 15%;
    font-size: 20px;
    text-align: center;
    color: #696969;
    font-weight: bold;
  }

  .infoAndTimeContainer {
    width: 85%;
    display: flex;
    flex-direction: column;
    font-size: 15px;
  }

  .infoContainer {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }

  .timeContainer {
    padding: 5px;
  }

  .timeContainer .propertyKey {
    font-size: 15px;
  }

  .propertyContainer {
    width: 200px;
    padding: 5px;
    font-size: 14px;
  }

  .propertyKey {
    color: black;
  }

  .propertyValue {
    color: #696969;
  }

  .deleteStyle:hover {
    color: red;
    cursor: pointer;
  }
</style>
