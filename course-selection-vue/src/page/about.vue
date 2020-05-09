<template>
    <div class="info">
      <div class="item">
        <div class="label">开发者：</div>
        <div class="value">卓林波(201625010529)</div>
      </div>
      <div class="item">
        <div class="label">邮箱：</div>
        <div class="value">1258488437@qq.com</div>
      </div>
      <div class="item">
        <div class="label"> wx：</div>
        <div class="value">qq1258488437</div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "about"
    }
</script>

<style scoped>
  .info{
    color: #696969;
    font-weight: bold;
    padding: 20px;
  }
  .item{
    display: flex;
    flex-direction: row;
    width: 400px;
    padding: 10px;
  }
  .item .label{
    width: 20%;
  }
  .item .value{
    width: 80%;
  }
</style>
