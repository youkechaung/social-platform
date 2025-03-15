`<template>
  <div class="messages">
    <a-row :gutter="16">
      <a-col :span="8">
        <a-card class="contacts-list">
          <template #title>消息列表</template>
          <a-list :dataSource="contacts" :loading="loading">
            <template #renderItem="{ item }">
              <a-list-item 
                class="contact-item" 
                :class="{ active: selectedContact?.id === item.id }"
                @click="selectContact(item)"
              >
                <a-list-item-meta>
                  <template #avatar>
                    <a-badge :count="item.unread">
                      <a-avatar :src="item.avatar" />
                    </a-badge>
                  </template>
                  <template #title>{{ item.name }}</template>
                  <template #description>
                    <div class="last-message">
                      <span>{{ item.lastMessage }}</span>
                      <span class="message-time">{{ item.lastMessageTime }}</span>
                    </div>
                  </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>

      <a-col :span="16">
        <a-card v-if="selectedContact" class="chat-window">
          <template #title>
            <span>{{ selectedContact.name }}</span>
          </template>
          <div class="messages-container" ref="messagesContainer">
            <div 
              v-for="msg in currentMessages" 
              :key="msg.id"
              class="message"
              :class="{ 'message-self': msg.isSelf }"
            >
              <a-avatar :src="msg.isSelf ? userAvatar : selectedContact.avatar" />
              <div class="message-content">
                <div class="message-text">{{ msg.content }}</div>
                <div class="message-time">{{ msg.time }}</div>
              </div>
            </div>
          </div>
          <div class="message-input">
            <a-input-group compact>
              <a-textarea
                v-model:value="newMessage"
                :rows="3"
                placeholder="输入消息..."
                @keypress.enter.prevent="sendMessage"
              />
              <a-button type="primary" @click="sendMessage">发送</a-button>
            </a-input-group>
          </div>
        </a-card>
        <a-empty v-else description="选择一个联系人开始聊天" />
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, nextTick, computed } from 'vue'
import { message } from 'ant-design-vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const userAvatar = computed(() => userStore.avatar)

const loading = ref(false)
const contacts = ref([])
const selectedContact = ref(null)
const currentMessages = ref([])
const newMessage = ref('')
const messagesContainer = ref(null)

const fetchContacts = async () => {
  loading.value = true
  try {
    // TODO: 接入实际API
    contacts.value = [
      {
        id: 1,
        name: '张三',
        avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=1',
        lastMessage: '好的，明天见！',
        lastMessageTime: '10:30',
        unread: 2
      },
      {
        id: 2,
        name: '李四',
        avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=2',
        lastMessage: '收到了，谢谢',
        lastMessageTime: '昨天',
        unread: 0
      }
    ]
  } catch (error) {
    message.error('获取联系人失败')
  } finally {
    loading.value = false
  }
}

const selectContact = async (contact) => {
  selectedContact.value = contact
  // TODO: 接入实际API获取聊天记录
  currentMessages.value = [
    {
      id: 1,
      content: '你好！',
      time: '10:00',
      isSelf: false
    },
    {
      id: 2,
      content: '你好！最近怎么样？',
      time: '10:01',
      isSelf: true
    }
  ]
  await nextTick()
  scrollToBottom()
}

const scrollToBottom = () => {
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

const sendMessage = async () => {
  if (!newMessage.value.trim()) {
    return
  }
  
  // TODO: 接入实际发送消息API
  currentMessages.value.push({
    id: Date.now(),
    content: newMessage.value,
    time: new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' }),
    isSelf: true
  })
  
  newMessage.value = ''
  await nextTick()
  scrollToBottom()
}

onMounted(() => {
  fetchContacts()
})
</script>

<style scoped>
.messages {
  padding: 20px 0;
  height: calc(100vh - 200px);
}

.contacts-list {
  height: 100%;
  overflow-y: auto;
}

.contact-item {
  cursor: pointer;
  transition: background-color 0.3s;
}

.contact-item:hover {
  background-color: #f5f5f5;
}

.contact-item.active {
  background-color: #e6f7ff;
}

.last-message {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.message-time {
  color: #999;
  font-size: 12px;
}

.chat-window {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f5f5f5;
  margin: -24px;
  margin-bottom: 0;
}

.message {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
}

.message-self {
  flex-direction: row-reverse;
}

.message-content {
  margin: 0 12px;
  max-width: 60%;
}

.message-text {
  padding: 10px 16px;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.message-self .message-text {
  background-color: #1890ff;
  color: white;
}

.message-input {
  margin: 0 -24px;
  margin-top: 24px;
  padding: 24px;
  border-top: 1px solid #f0f0f0;
  background-color: white;
}

.message-input .ant-input-group {
  display: flex;
}

.message-input .ant-input-group .ant-btn {
  margin-left: 8px;
}
</style>`
