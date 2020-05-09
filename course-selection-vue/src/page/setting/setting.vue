<template>
  <div style="padding: 20px;width:800px;">
    <div>
      <h2>评教状态：<span style="color: green">{{status}}</span></h2>
      <h4>{{timeSpan}} : {{timeDifferent}}</h4>
    </div>
    <el-form :model="form" ref="myForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="评教时间设置" prop="time">
        <el-date-picker
          value-format="yyyy-MM-dd HH:mm:ss"
          @change="timeSelect"
          v-model="time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
        <el-button type="primary" @click="submitForm()">确定设置</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
    export default {
        name: "setting",
        data() {
            return {
                status: '已结束',
                timeSpan: '已结束',
                timeDifferent: '0天0时0分0秒',
                commentStartTime: 0,
                commentEndTime: 0,
                form: {
                    id: '',
                    commentStartTime: '',
                    commentEndTime: '',
                },
                time: [],
            };
        },
        created: function () {
            this.getCommentTime()
            setInterval(() => {
                let now = Date.now();
                if (now < this.commentStartTime) {
                    this.status = '未开始'
                    this.timeSpan = '距开始时间还有'
                    this.timeDifferent = this.getTimeDiff(now, this.commentStartTime)
                } else if (now > this.commentEndTime) {
                    this.status = '已结束'
                    this.timeSpan = '已结束'
                    this.timeDifferent = this.getTimeDiff(now, this.commentEndTime)
                } else {
                    this.status = '进行中'
                    this.timeSpan = '距结束时间还有'
                    this.timeDifferent = this.getTimeDiff(now, this.commentEndTime)
                }
            }, 1000)
        },
        methods: {
            test(){
                this.$axios.get('/user_course/users/21/').then((res) => {
                    this.getCommentTime()
                    this.$message({
                        message: res.data.info,
                        type: 'success'
                    })
                })
            },
            getTimeDiff(time1, time2) {
                let timeDifferent = Math.abs(time1 - time2)
                timeDifferent = Math.round(timeDifferent / 1000)
                let day = Math.floor(timeDifferent / (3600 * 24))
                let hour = Math.floor(timeDifferent % (3600 * 24) / 3600)
                let minute = Math.floor(timeDifferent % 3600 / 60)
                let second = timeDifferent % 60
                return day + ' 天 ' + hour + ' 时 ' + minute + ' 分 ' + second + ' 秒 '
            },
            timeSelect() {
                if (this.time == null) {
                    return
                }
                this.form.commentStartTime = this.time[0]
                this.form.commentEndTime = this.time[1]
            },
            getCommentTime() {
                this.$axios.get('setting').then((res) => {
                    let setting = res.data.data
                    let time = [setting.commentStartTime, setting.commentEndTime]
                    this.time = time
                    this.form.commentStartTime = setting.commentStartTime
                    this.form.commentEndTime = setting.commentEndTime
                    this.commentStartTime = Date.parse(setting.commentStartTime)
                    this.commentEndTime = Date.parse(setting.commentEndTime)
                })
            },
            submitForm() {
                this.$axios.put('setting/', this.form).then((res) => {
                    this.getCommentTime()
                    this.$message({
                        message: res.data.info,
                        type: 'success'
                    })
                })
            },
        }
    }
</script>

<style scoped>

</style>
