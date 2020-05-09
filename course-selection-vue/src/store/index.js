
//vuex暂时没用到
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({

  state: {
    // 存储token
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  /* actions: {
     changeLogin(ctx,Authorization){
       ctx.commit('changeLogin',Authorization);
     }
   },*/
  mutations: {
    // 修改token，并将token存入localStorage
    saveToken(token) {
      state.token = token;
      console.log("store/index.js---到这里了!");
      localStorage.setItem('token', token);
    }
  }
});


