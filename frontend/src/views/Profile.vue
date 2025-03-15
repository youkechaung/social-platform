`<template>
  <div class="profile">
    <a-row :gutter="[16, 16]">
      <a-col :span="8">
        <a-card>
          <div class="profile-header">
            <a-avatar :size="100" :src="userStore.avatar" />
            <h2>{{ userStore.username }}</h2>
            <p class="bio">{{ userInfo.bio || '这个人很懒，还没有写简介' }}</p>
          </div>
          <a-divider />
          <div class="profile-stats">
            <div class="stat-item">
              <div class="stat-value">{{ userInfo.posts }}</div>
              <div class="stat-label">帖子</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ userInfo.followers }}</div>
              <div class="stat-label">关注者</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ userInfo.following }}</div>
              <div class="stat-label">关注</div>
            </div>
          </div>
          <a-divider />
          <div class="profile-info">
            <p><user-outlined /> {{ userInfo.location || '未设置地区' }}</p>
            <p><calendar-outlined /> 加入于 {{ userInfo.joinDate }}</p>
          </div>
          <a-button type="primary" block @click="showEditModal">
            编辑资料
          </a-button>
        </a-card>
      </a-col>

      <a-col :span="16">
        <a-tabs v-model:activeKey="activeTab">
          <a-tab-pane key="posts" tab="帖子">
            <a-list
              :dataSource="posts"
              :loading="loading"
              itemLayout="vertical"
            >
              <template #renderItem="{ item }">
                <a-list-item>
                  <template #actions>
                    <span>
                      <like-outlined :style="{ color: item.liked ? '#1890ff' : '' }" />
                      {{ item.likes }}
                    </span>
                    <span>
                      <comment-outlined />
                      {{ item.comments }}
                    </span>
                    <span>
                      <share-alt-outlined />
                    </span>
                  </template>
                  <a-list-item-meta>
                    <template #title>
                      <a>{{ item.title }}</a>
                    </template>
                    <template #description>
                      {{ item.createTime }}
                    </template>
                  </a-list-item-meta>
                  {{ item.content }}
                </a-list-item>
              </template>
            </a-list>
          </a-tab-pane>
          
          <a-tab-pane key="likes" tab="赞过">
            <a-list
              :dataSource="likedPosts"
              :loading="loading"
              itemLayout="vertical"
            >
              <template #renderItem="{ item }">
                <a-list-item>
                  <template #actions>
                    <span>
                      <like-outlined style="color: #1890ff" />
                      {{ item.likes }}
                    </span>
                    <span>
                      <comment-outlined />
                      {{ item.comments }}
                    </span>
                  </template>
                  <a-list-item-meta>
                    <template #avatar>
                      <a-avatar :src="item.authorAvatar" />
                    </template>
                    <template #title>
                      <a>{{ item.authorName }}</a>
                    </template>
                    <template #description>
                      {{ item.createTime }}
                    </template>
                  </a-list-item-meta>
                  {{ item.content }}
                </a-list-item>
              </template>
            </a-list>
          </a-tab-pane>
        </a-tabs>
      </a-col>
    </a-row>

    <a-modal
      v-model:visible="editModalVisible"
      title="编辑个人资料"
      @ok="handleEditProfile"
    >
      <a-form :model="editForm" layout="vertical">
        <a-form-item label="用户名">
          <a-input v-model:value="editForm.username" />
        </a-form-item>
        <a-form-item label="简介">
          <a-textarea v-model:value="editForm.bio" :rows="4" />
        </a-form-item>
        <a-form-item label="地区">
          <a-input v-model:value="editForm.location" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { useUserStore } from '@/stores/user'
import {
  UserOutlined,
  CalendarOutlined,
  LikeOutlined,
  CommentOutlined,
  ShareAltOutlined
} from '@ant-design/icons-vue'

const userStore = useUserStore()
const loading = ref(false)
const activeTab = ref('posts')
const editModalVisible = ref(false)

const userInfo = ref({
  bio: '热爱生活，分享快乐',
  posts: 42,
  followers: 128,
  following: 96,
  location: '北京',
  joinDate: '2024年1月'
})

const editForm = ref({
  username: userStore.username,
  bio: userInfo.value.bio,
  location: userInfo.value.location
})

const posts = ref([])
const likedPosts = ref([])

const fetchPosts = async () => {
  loading.value = true
  try {
    // TODO: 接入实际API
    posts.value = [
      {
        id: 1,
        title: '分享一个有趣的发现',
        content: '今天在公园里看到了很多小松鼠...',
        createTime: '2天前',
        likes: 15,
        comments: 3,
        liked: true
      },
      {
        id: 2,
        title: '新书推荐',
        content: '最近在读的一本书很不错...',
        createTime: '1周前',
        likes: 8,
        comments: 2,
        liked: false
      }
    ]
  } catch (error) {
    message.error('获取帖子失败')
  } finally {
    loading.value = false
  }
}

const fetchLikedPosts = async () => {
  loading.value = true
  try {
    // TODO: 接入实际API
    likedPosts.value = [
      {
        id: 1,
        authorName: '王五',
        authorAvatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=3',
        content: '推荐一部很棒的电影...',
        createTime: '3天前',
        likes: 25,
        comments: 8
      }
    ]
  } catch (error) {
    message.error('获取点赞帖子失败')
  } finally {
    loading.value = false
  }
}

const showEditModal = () => {
  editModalVisible.value = true
}

const handleEditProfile = async () => {
  try {
    // TODO: 接入实际API
    Object.assign(userInfo.value, {
      bio: editForm.value.bio,
      location: editForm.value.location
    })
    userStore.setUsername(editForm.value.username)
    message.success('更新成功')
    editModalVisible.value = false
  } catch (error) {
    message.error('更新失败')
  }
}

onMounted(() => {
  fetchPosts()
  fetchLikedPosts()
})
</script>

<style scoped>
.profile {
  padding: 20px 0;
}

.profile-header {
  text-align: center;
  margin-bottom: 20px;
}

.profile-header h2 {
  margin: 16px 0 8px;
}

.bio {
  color: #666;
}

.profile-stats {
  display: flex;
  justify-content: space-around;
  text-align: center;
}

.stat-item {
  padding: 0 12px;
}

.stat-value {
  font-size: 20px;
  font-weight: bold;
  color: #1890ff;
}

.stat-label {
  color: #666;
  font-size: 14px;
}

.profile-info {
  color: #666;
}

.profile-info p {
  margin-bottom: 8px;
}

.profile-info .anticon {
  margin-right: 8px;
}
</style>`
