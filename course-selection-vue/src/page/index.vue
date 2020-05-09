<template>
  <div class="container">
    <div class="mainContainer">
      <div>
        <img src="../pic/logo.png"/>
      </div>
      <div>
        <h2>欢迎使用SCAU选课系统</h2>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "index"
    }
</script>

<style scoped>
  @import "../style/common.css";

  .mainContainer {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
</style>
