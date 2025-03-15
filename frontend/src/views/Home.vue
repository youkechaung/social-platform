`<template>
  <div class="home">
    <a-row :gutter="[16, 16]">
      <a-col :span="16">
        <a-card class="post-form">
          <a-form layout="vertical">
            <a-form-item>
              <a-textarea
                v-model:value="newPost"
                :rows="4"
                placeholder="分享新鲜事..."
              />
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="handlePost">发布</a-button>
            </a-form-item>
          </a-form>
        </a-card>

        <a-list
          class="post-list"
          :dataSource="posts"
          :loading="loading"
          itemLayout="vertical"
        >
          <template #renderItem="{ item }">
            <a-list-item>
              <template #actions>
                <span @click="handleLike(item)">
                  <like-outlined :style="{ color: item.liked ? '#1890ff' : '' }" />
                  {{ item.likes }}
                </span>
                <span @click="handleComment(item)">
                  <comment-outlined />
                  {{ item.comments }}
                </span>
                <span @click="handleShare(item)">
                  <share-alt-outlined />
                </span>
              </template>
              <a-list-item-meta>
                <template #avatar>
                  <a-avatar :src="item.avatar" />
                </template>
                <template #title>
                  <a>{{ item.username }}</a>
                </template>
                <template #description>
                  {{ item.createTime }}
                </template>
              </a-list-item-meta>
              {{ item.content }}
            </a-list-item>
          </template>
        </a-list>
      </a-col>

      <a-col :span="8">
        <a-card title="热门话题" :bordered="false">
          <a-list :dataSource="hotTopics" size="small">
            <template #renderItem="{ item }">
              <a-list-item>
                <a href="#">#{{ item.topic }}</a>
                <span class="topic-count">{{ item.count }}次讨论</span>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { LikeOutlined, CommentOutlined, ShareAltOutlined } from '@ant-design/icons-vue'

const loading = ref(false)
const newPost = ref('')
const posts = ref([])
const hotTopics = ref([
  { topic: '科技创新', count: 1234 },
  { topic: '美食分享', count: 890 },
  { topic: '旅行日记', count: 567 },
  { topic: '生活趣事', count: 432 }
])

const fetchPosts = async () => {
  loading.value = true
  try {
    // TODO: 接入实际API
    posts.value = [
      {
        id: 1,
        username: '张三',
        avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=1',
        content: '今天天气真好！',
        createTime: '2小时前',
        likes: 12,
        comments: 5,
        liked: false
      },
      {
        id: 2,
        username: '李四',
        avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=2',
        content: '分享一个有趣的发现...',
        createTime: '4小时前',
        likes: 8,
        comments: 3,
        liked: true
      }
    ]
  } catch (error) {
    message.error('获取帖子失败')
  } finally {
    loading.value = false
  }
}

const handlePost = async () => {
  if (!newPost.value.trim()) {
    message.warning('请输入内容')
    return
  }
  // TODO: 接入发帖API
  message.success('发布成功')
  newPost.value = ''
  await fetchPosts()
}

const handleLike = (post: any) => {
  post.liked = !post.liked
  post.likes += post.liked ? 1 : -1
  // TODO: 接入点赞API
}

const handleComment = (post: any) => {
  // TODO: 实现评论功能
}

const handleShare = (post: any) => {
  // TODO: 实现分享功能
}

onMounted(() => {
  fetchPosts()
})
</script>

<style scoped>
.home {
  padding: 20px 0;
}

.post-form {
  margin-bottom: 20px;
}

.post-list {
  background: white;
  padding: 24px;
  border-radius: 2px;
}

.topic-count {
  color: #999;
  font-size: 12px;
}
</style>`
