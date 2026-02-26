<template>
  <view class="profile-container">
    <view class="header">
      <view class="avatar">
        <image :src="profile.avatar || '/static/default-avatar.png'" mode="aspectFill"></image>
      </view>
      <text class="name">{{ profile.realName || '未完善' }}</text>
      <text class="phone" v-if="user.phone">{{ maskPhone(user.phone) }}</text>
    </view>

    <view class="info-card">
      <view class="info-title">基本信息</view>
      <view class="info-row">
        <text class="label">姓名</text>
        <text class="value">{{ profile.realName || '-' }}</text>
      </view>
      <view class="info-row">
        <text class="label">性别</text>
        <text class="value">{{ genderText }}</text>
      </view>
      <view class="info-row">
        <text class="label">年龄</text>
        <text class="value">{{ profile.age }}岁</text>
      </view>
      <view class="info-row">
        <text class="label">身份证号</text>
        <text class="value">{{ profile.idCardNumberMasked || '-' }}</text>
      </view>
      <view class="info-row">
        <text class="label">民族</text>
        <text class="value">{{ profile.nation || '-' }}</text>
      </view>
      <view class="info-row">
        <text class="label">出生日期</text>
        <text class="value">{{ profile.birthday || '-' }}</text>
      </view>
      <view class="info-row">
        <text class="label">地址</text>
        <text class="value">{{ profile.address || '-' }}</text>
      </view>
    </view>

    <view class="idcard-card">
      <view class="card-title">身份证照片</view>
      <view class="photo-grid">
        <view class="photo-item" @click="uploadPhoto(1)">
          <image v-if="profile.idCardFrontPhoto" :src="profile.idCardFrontPhoto" mode="aspectFill"></image>
          <view v-else class="photo-placeholder">
            <text class="icon">+</text>
            <text class="text">正面</text>
          </view>
        </view>
        <view class="photo-item" @click="uploadPhoto(2)">
          <image v-if="profile.idCardBackPhoto" :src="profile.idCardBackPhoto" mode="aspectFill"></image>
          <view v-else class="photo-placeholder">
            <text class="icon">+</text>
            <text class="text">背面</text>
          </view>
        </view>
      </view>
    </view>

    <view class="contact-card">
      <view class="card-title">紧急联系人</view>
      <view class="info-row">
        <text class="label">姓名</text>
        <text class="value">{{ profile.emergencyContactName || '-' }}</text>
      </view>
      <view class="info-row">
        <text class="label">电话</text>
        <text class="value">{{ profile.emergencyContactPhoneMasked || '-' }}</text>
      </view>
    </view>

    <button class="edit-btn" @click="editProfile">编辑档案</button>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const user = ref({})
const profile = ref({})

onMounted(() => {
  loadProfile()
})

const loadProfile = async () => {
  try {
    user.value = await userStore.getUserInfo()
    const data = await userStore.getUserProfile()
    profile.value = data
  } catch (error) {
    console.error('加载档案失败', error)
  }
}

const genderText = computed(() => {
  const map = { 0: '未知', 1: '男', 2: '女' }
  return map[profile.value.gender] || '-'
})

const uploadPhoto = (type) => {
  uni.chooseImage({
    count: 1,
    success: (res) => {
      uni.uploadFile({
        url: 'http://localhost:8080/api/user/upload-idcard',
        filePath: res.tempFilePaths[0],
        name: 'file',
        formData: { type },
        header: {
          'Authorization': 'Bearer ' + userStore.token
        },
        success: () => {
          uni.showToast({ title: '上传成功', icon: 'success' })
          loadProfile()
        },
        fail: () => {
          uni.showToast({ title: '上传失败', icon: 'none' })
        }
      })
    }
  })
}

const editProfile = () => {
  uni.navigateTo({ url: '/pages/profile/edit' })
}

const maskPhone = (phone) => {
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}
</script>

<style scoped lang="scss">
.profile-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20rpx;
}

.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 60rpx 40rpx;
  border-radius: 20rpx;
  margin-bottom: 20rpx;
  text-align: center;
}

.avatar {
  width: 120rpx;
  height: 120rpx;
  margin: 0 auto 20rpx;
  border-radius: 50%;
  overflow: hidden;
  border: 4rpx solid rgba(255, 255, 255, 0.5);
}

.avatar image {
  width: 100%;
  height: 100%;
}

.name {
  display: block;
  font-size: 36rpx;
  font-weight: bold;
  color: white;
  margin-bottom: 10rpx;
}

.phone {
  display: block;
  font-size: 28rpx;
  color: rgba(255, 255, 255, 0.8);
}

.info-card,
.idcard-card,
.contact-card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.info-title,
.card-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 30rpx;
  padding-left: 10rpx;
  border-left: 6rpx solid #667eea;
}

.info-row {
  display: flex;
  justify-content: space-between;
  padding: 25rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}

.info-row:last-child {
  border-bottom: none;
}

.label {
  font-size: 28rpx;
  color: #666;
}

.value {
  font-size: 28rpx;
  color: #333;
  font-weight: 500;
}

.photo-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30rpx;
}

.photo-item {
  aspect-ratio: 1.58;
  border-radius: 10rpx;
  overflow: hidden;
  border: 2rpx dashed #ddd;
}

.photo-item image {
  width: 100%;
  height: 100%;
}

.photo-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: #f9f9f9;
}

.photo-placeholder .icon {
  font-size: 60rpx;
  color: #ddd;
}

.photo-placeholder .text {
  font-size: 24rpx;
  color: #999;
  margin-top: 10rpx;
}

.edit-btn {
  width: calc(100% - 40rpx);
  margin: 40rpx 20rpx;
  height: 88rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 10rpx;
  color: white;
  font-size: 32rpx;
  font-weight: bold;
}
</style>
