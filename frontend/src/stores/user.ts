import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const username = ref('')
  const avatar = ref('https://api.dicebear.com/7.x/avataaars/svg?seed=user')
  const token = ref('')
  const isAuthenticated = ref(false)

  function setUser(userData: { username: string; avatar?: string; token: string }) {
    username.value = userData.username
    if (userData.avatar) {
      avatar.value = userData.avatar
    }
    token.value = userData.token
    isAuthenticated.value = true
  }

  function setUsername(newUsername: string) {
    username.value = newUsername
  }

  function logout() {
    username.value = ''
    avatar.value = 'https://api.dicebear.com/7.x/avataaars/svg?seed=user'
    token.value = ''
    isAuthenticated.value = false
  }

  return {
    username,
    avatar,
    token,
    isAuthenticated,
    setUser,
    setUsername,
    logout
  }
})
