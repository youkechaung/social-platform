`<template>
  <div class="login-container">
    <a-card class="login-card">
      <h1>登录</h1>
      <a-form
        :model="formState"
        @finish="handleSubmit"
        layout="vertical"
      >
        <a-form-item
          label="用户名"
          name="username"
          :rules="[{ required: true, message: '请输入用户名' }]"
        >
          <a-input v-model:value="formState.username">
            <template #prefix>
              <user-outlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: '请输入密码' }]"
        >
          <a-input-password v-model:value="formState.password">
            <template #prefix>
              <lock-outlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
          <a class="forgot-link" @click="handleForgotPassword">忘记密码？</a>
        </a-form-item>

        <a-form-item>
          <a-button type="primary" html-type="submit" :loading="loading" block>
            登录
          </a-button>
        </a-form-item>

        <div class="register-link">
          还没有账号？
          <router-link to="/auth/register">立即注册</router-link>
        </div>
      </a-form>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { message } from 'ant-design-vue'
import { useRouter, useRoute } from 'vue-router'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const loading = ref(false)
const formState = reactive({
  username: '',
  password: '',
  remember: false
})

const handleSubmit = async () => {
  loading.value = true
  try {
    // TODO: 接入实际登录API
    // 模拟登录成功
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    userStore.setUser({
      username: formState.username,
      token: 'mock-token'
    })
    
    message.success('登录成功')
    
    const redirectPath = route.query.redirect as string || '/'
    router.push(redirectPath)
  } catch (error) {
    message.error('登录失败，请检查用户名和密码')
  } finally {
    loading.value = false
  }
}

const handleForgotPassword = () => {
  message.info('密码重置功能开发中')
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 24px;
}

h1 {
  text-align: center;
  margin-bottom: 32px;
}

.forgot-link {
  float: right;
}

.register-link {
  text-align: center;
  margin-top: 16px;
}
</style>`
