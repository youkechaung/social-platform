`<template>
  <div class="register-container">
    <a-card class="register-card">
      <h1>注册</h1>
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
          label="邮箱"
          name="email"
          :rules="[
            { required: true, message: '请输入邮箱' },
            { type: 'email', message: '请输入有效的邮箱地址' }
          ]"
        >
          <a-input v-model:value="formState.email">
            <template #prefix>
              <mail-outlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          label="密码"
          name="password"
          :rules="[
            { required: true, message: '请输入密码' },
            { min: 6, message: '密码长度至少6位' }
          ]"
        >
          <a-input-password v-model:value="formState.password">
            <template #prefix>
              <lock-outlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item
          label="确认密码"
          name="confirmPassword"
          :rules="[
            { required: true, message: '请确认密码' },
            { validator: validateConfirmPassword }
          ]"
        >
          <a-input-password v-model:value="formState.confirmPassword">
            <template #prefix>
              <lock-outlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-checkbox v-model:checked="formState.agreement">
            我已阅读并同意
            <a @click="showAgreement">服务条款</a>
          </a-checkbox>
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            :loading="loading"
            :disabled="!formState.agreement"
            block
          >
            注册
          </a-button>
        </a-form-item>

        <div class="login-link">
          已有账号？
          <router-link to="/auth/login">立即登录</router-link>
        </div>
      </a-form>
    </a-card>

    <a-modal
      v-model:visible="agreementVisible"
      title="服务条款"
      @ok="handleAgreementOk"
    >
      <p>欢迎使用我们的社交平台！</p>
      <p>使用本服务，即表示您同意遵守以下条款：</p>
      <ol>
        <li>遵守相关法律法规</li>
        <li>尊重他人隐私</li>
        <li>不发布违法或不当内容</li>
        <li>保护账户安全</li>
      </ol>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { UserOutlined, LockOutlined, MailOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const agreementVisible = ref(false)

const formState = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  agreement: false
})

const validateConfirmPassword = async (_rule: any, value: string) => {
  if (value !== formState.password) {
    throw new Error('两次输入的密码不一致')
  }
}

const handleSubmit = async () => {
  if (!formState.agreement) {
    message.warning('请先同意服务条款')
    return
  }

  loading.value = true
  try {
    // TODO: 接入实际注册API
    // 模拟注册成功
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    userStore.setUser({
      username: formState.username,
      token: 'mock-token'
    })
    
    message.success('注册成功')
    router.push('/')
  } catch (error) {
    message.error('注册失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

const showAgreement = () => {
  agreementVisible.value = true
}

const handleAgreementOk = () => {
  agreementVisible.value = false
  formState.agreement = true
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
}

.register-card {
  width: 100%;
  max-width: 400px;
  padding: 24px;
}

h1 {
  text-align: center;
  margin-bottom: 32px;
}

.login-link {
  text-align: center;
  margin-top: 16px;
}
</style>`
