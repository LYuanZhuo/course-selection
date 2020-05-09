export default {
  host: 'http://localhost:8081/',//本地服务器ip地址
  //host: 'http://47.97.91.13:8081/',//远程服务器ip地址
  colleges: [
    '数学与信息学院（软件学院）',
    '工程学院',
    '水利与土木工程学院',
    '艺术学院',
    '经济管理学院',
    '电子工程学院',
    '农学院',
    '食品学院',
    '海洋学院',
    '外国语学院',
    '园艺学院',
    '林学与风景园林学院',
    '兽医学院',
    '生命科学学院',
    '资源环境学院',
    '材料与能源学院',
    '公共管理学院',
    '马克思主义学院',
    '继续教育学院',
    '人文与法学学院',
    '动物科学学院'
  ],
  buildings: ['教一', '教二', '教三', '教四', '教五', '教六'],
  getGrades(num) {
    let len = 4
    if (num!=undefined){
      len = num;
    }
    let grades = []
    let now = new Date()
    let year = now.getFullYear()
    let month = now.getMonth()+1
    if(month<=7){
      year--
    }
    for (let i = 0; i < len; i++) {
      grades.push((year - i) + '')
    }
    return grades
  },
  setNow(listParams) {
    let now = new Date()
    let year = now.getFullYear()
    let month = now.getMonth() + 1
    let quarter = '秋季'
    if (month >= 3 && month <= 8) {
      quarter = '春季'
    }
    listParams.year = year + ''
    listParams.quarter = quarter
  }
}
