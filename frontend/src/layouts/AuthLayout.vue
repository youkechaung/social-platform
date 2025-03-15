`<template>
  <div class="auth-layout">
    <div class="auth-background"></div>
    <div class="auth-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.auth-layout {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
}

.auth-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, #1890ff 0%, #722ed1 100%);
  opacity: 0.1;
  z-index: 0;
}

.auth-content {
  position: relative;
  z-index: 1;
}
</style>`
