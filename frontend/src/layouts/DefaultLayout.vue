`<template>
  <a-layout class="layout">
    <a-layout-header class="header">
      <div class="logo">Social Platform</div>
      <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        class="menu"
      >
        <a-menu-item key="home">
          <router-link to="/">
            <home-outlined /> 首页
          </router-link>
        </a-menu-item>
        <a-menu-item key="messages">
          <router-link to="/messages">
            <message-outlined /> 消息
          </router-link>
        </a-menu-item>
        <a-menu-item key="profile">
          <router-link to="/profile">
            <user-outlined /> 个人中心
          </router-link>
        </a-menu-item>
      </a-menu>
      <div class="header-right">
        <a-dropdown>
          <a-avatar :src="userStore.avatar" />
          <template #overlay>
            <a-menu>
              <a-menu-item key="settings">
                <setting-outlined /> 设置
              </a-menu-item>
              <a-menu-item key="logout" @click="handleLogout">
                <logout-outlined /> 退出
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </div>
    </a-layout-header>

    <a-layout-content class="content">
      <router-view></router-view>
    </a-layout-content>

    <a-layout-footer class="footer">
      Social Platform ©{{ new Date().getFullYear() }} Created by Your Team
    </a-layout-footer>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import {
  HomeOutlined,
  MessageOutlined,
  UserOutlined,
  SettingOutlined,
  LogoutOutlined
} from '@ant-design/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const selectedKeys = ref<string[]>(['home'])

const handleLogout = async () => {
  await userStore.logout()
  router.push('/auth/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.header {
  display: flex;
  align-items: center;
  padding: 0 50px;
}

.logo {
  color: white;
  font-size: 20px;
  font-weight: bold;
  margin-right: 40px;
}

.menu {
  flex: 1;
}

.header-right {
  margin-left: auto;
}

.content {
  padding: 24px 50px;
  min-height: 280px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.footer {
  text-align: center;
}

:deep(.ant-layout-header) {
  background: #001529;
}

:deep(.ant-menu-dark) {
  background: transparent;
}
</style>`
