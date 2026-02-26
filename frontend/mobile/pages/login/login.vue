<template>
  <view class="login-container">
    <view class="header">
      <text class="title">家庭健康档案</text>
      <text class="subtitle">让健康管理更智能</text>
    </view>

    <view class="form">
      <view class="form-item">
        <text class="label">手机号</text>
        <input
          class="input"
          type="number"
          v-model="phone"
          placeholder="请输入手机号"
          maxlength="11"
        />
      </view>

      <view class="form-item">
        <text class="label">密码</text>
        <input
          class="input"
          type="password"
          v-model="password"
          placeholder="请输入密码"
          maxlength="20"
        />
      </view>

      <button class="login-btn" @click="handleLogin" :disabled="loading">
        {{ loading ? '登录中...' : '登录' }}
      </button>

      <view class="footer">
        <text class="link" @click="goToRegister">还没有账号？立即注册</text>
      </view>
    </view>

    <!-- 测试账号提示 -->
    <view class="test-account">
      <text class="test-title">测试账号：</text>
      <text class="test-info">手机号：13800138000</text>
      <text class="test-info">密码：123456</text>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const phone = ref('')
const password = ref('')
const loading = ref(false)

const handleLogin = async () => {
  if (!phone.value) {
    uni.showToast({ title: '请输入手机号', icon: 'none' })
    return
  }
  if (!password.value) {
    uni.showToast({ title: '请输入密码', icon: 'none' })
    return
  }

  loading.value = true
  try {
    await userStore.login({
      phone: phone.value,
      password: password.value
    })
    uni.showToast({ title: '登录成功', icon: 'success' })
    setTimeout(() => {
      uni.switchTab({ url: '/pages/index/index' })
    }, 1000)
  } catch (error) {
    uni.showToast({ title: error.message || '登录失败', icon: 'none' })
  } finally {
    loading.value = false
  }
}

const goToRegister = () => {
  uni.navigateTo({ url: '/pages/register/register' })
}
</script>

<style scoped lang="scss">
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 100rpx 60rpx;
}

.header {
  text-align: center;
  margin-bottom: 100rpx;
}

.title {
  display: block;
  font-size: 48rpx;
  font-weight: bold;
  color: white;
  margin-bottom: 20rpx;
}

.subtitle {
  display: block;
  font-size: 28rpx;
  color: rgba(255, 255, 255, 0.8);
}

.form {
  background: white;
  border-radius: 20rpx;
  padding: 60rpx 40rpx;
}

.form-item {
  margin-bottom: 40rpx;
}

.label {
  display: block;
  font-size: 28rpx;
  color: #333;
  margin-bottom: 20rpx;
}

.input {
  width: 100%;
  height: 80rpx;
  padding: 0 20rpx;
  border: 1rpx solid #e0e0e0;
  border-radius: 10rpx;
  font-size: 28rpx;
  background: #f9f9f9;
}

.login-btn {
  width: 100%;
  height: 88rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 10rpx;
  color: white;
  font-size: 32rpx;
  font-weight: bold;
  margin-top: 40rpx;
}

.login-btn:disabled {
  opacity: 0.6;
}

.footer {
  text-align: center;
  margin-top: 40rpx;
}

.link {
  font-size: 28rpx;
  color: #667eea;
}

.test-account {
  margin-top: 60rpx;
  padding: 30rpx;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 10rpx;
}

.test-title {
  display: block;
  font-size: 24rpx;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 10rpx;
}

.test-info {
  display: block;
  font-size: 24rpx;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 5rpx;
}
</style>
