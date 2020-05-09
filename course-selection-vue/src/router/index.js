import Vue from 'vue'
import Router from 'vue-router'
import {Message} from "element-ui";

const login = () => import('../page/login')
const home = () => import('../page/home')
const teacher_list = () => import('../page/teacher/teacher_list')
const teacher_add = () => import('../page/teacher/teacher_add')
const classroom_list = () => import('../page/classroom/classroom_list')
const classroom_add = () => import('../page/classroom/classroom_add')
const basecourse_list = () => import('../page/basecourse/basecourse_list')
const basecourse_add = () => import('../page/basecourse/basecourse_add')
const basecourse_detail = () => import('../page/basecourse/basecourse_detail')
const course_list = () => import('../page/course/course_list')
const course_add = () => import('../page/course/course_add')
const course_detail = () => import('../page/course/course_detail')
const item_list = () => import('../page/item/item_list')
const item_add = () => import('../page/item/item_add')
const item_detail = () => import('../page/item/item_detail')
const user_add = () => import('../page/user/user_add')
const user_list = () => import('../page/user/user_list')
const setting = () => import('../page/setting/setting')
const about = () => import('../page/about')
const index = () => import('../page/index')
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path:'/',
      redirect:'/home'
    },
    {
      path: '/login',
      component: login,
    },
    {
      path: '/home',
      component: home,
      redirect:'/index',
      children:[
        {
          path: '/index',
          component: index,
        },,
        {
          path:'/teacher_list',
          component:teacher_list
        },
        {
          path:'/teacher_add',
          component:teacher_add
        },
        {
          path:'/classroom_list',
          component:classroom_list
        },
        {
          path:'/classroom_add',
          component:classroom_add
        },
        {
          path:'/basecourse_list',
          component:basecourse_list
        },
        {
          path:'/basecourse_add',
          component:basecourse_add
        },
        {
          path:'/basecourse_detail',
          component:basecourse_detail
        },
        {
          path:'/item_list',
          component:item_list
        },
        {
          path:'/item_detail',
          component:item_detail
        },
        {
          path:'/item_add',
          component:item_add
        },
        {
          path:'/course_list',
          component:course_list
        },
        {
          path:'/course_add',
          component:course_add
        },
        {
          path:'/course_detail',
          component:course_detail
        },
        {
          path:'/user_list',
          component:user_list
        },
        {
          path:'/user_add',
          component:user_add
        },
        {
          path:'/setting',
          component:setting
        },
        {
          path:'/about',
          component:about
        },
      ]
    }
  ]
})

//路由拦截器 （在进入当前请求的路径前判断有没有token）
router.beforeEach((to, from, next) => {
  console.log("router的beforeEach")
  console.log("本地缓存的token: " + localStorage.getItem('token'));
  if (to.path === '/login') {
    localStorage.removeItem('token');
    next();
  } else {
    let token = localStorage.getItem('token');
    if (token === null || token === '') {
      Message.warning("你还没登录！")
      next('/login');
    } else {
      next();
    }
  }
});

export default router
