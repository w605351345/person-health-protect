<template>
  <view class="register-container">
    <view class="header">
      <text class="title">注册账号</text>
    </view>

    <view class="form">
      <view class="form-item">
        <text class="label">手机号</text>
        <input
          class="input"
          type="number"
          v-model="form.phone"
          placeholder="请输入手机号"
          maxlength="11"
        />
      </view>

      <view class="form-item">
        <text class="label">验证码</text>
        <view class="verify-code">
          <input
            class="input"
            type="number"
            v-model="form.verificationCode"
            placeholder="请输入验证码"
            maxlength="6"
          />
          <button class="code-btn" @click="sendCode" :disabled="codeDisabled">
            {{ codeText }}
          </button>
        </view>
      </view>

      <view class="form-item">
        <text class="label">密码</text>
        <input
          class="input"
          type="password"
          v-model="form.password"
          placeholder="请输入密码（6-20位，包含字母和数字）"
          maxlength="20"
        />
      </view>

      <view class="form-item">
        <text class="label">确认密码</text>
        <input
          class="input"
          type="password"
          v-model="form.confirmPassword"
          placeholder="请再次输入密码"
          maxlength="20"
        />
      </view>

      <button class="register-btn" @click="handleRegister" :disabled="loading">
        {{ loading ? '注册中...' : '注册' }}
      </button>

      <view class="footer">
        <text class="link" @click="goToLogin">已有账号？立即登录</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'

const form = ref({
  phone: '',
  verificationCode: '',
  password: '',
  confirmPassword: ''
})

const loading = ref(false)
const codeDisabled = ref(false)
const codeText = ref('获取验证码')

const sendCode = () => {
  if (!form.value.phone) {
    uni.showToast({ title: '请输入手机号', icon: 'none' })
    return
  }
  if (!/^1[3-9]\d{9}$/.test(form.value.phone)) {
    uni.showToast({ title: '手机号格式不正确', icon: 'none' })
    return
  }

  // 模拟发送验证码
  uni.showToast({ title: '验证码已发送', icon: 'success' })

  codeDisabled.value = true
  let countdown = 60
  const timer = setInterval(() => {
    countdown--
    codeText.value = `${countdown}秒后重试`
    if (countdown <= 0) {
      clearInterval(timer)
      codeText.value = '获取验证码'
      codeDisabled.value = false
    }
  }, 1000)
}

const handleRegister = async () => {
  if (!form.value.phone) {
    uni.showToast({ title: '请输入手机号', icon: 'none' })
    return
  }
  if (!/^1[3-9]\d{9}$/.test(form.value.phone)) {
    uni.showToast({ title: '手机号格式不正确', icon: 'none' })
    return
  }
  if (!form.value.verificationCode) {
    uni.showToast({ title: '请输入验证码', icon: 'none' })
    return
  }
  if (!form.value.password) {
    uni.showToast({ title: '请输入密码', icon: 'none' })
    return
  }
  if (!/^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{6,20}$/.test(form.value.password)) {
    uni.showToast({ title: '密码必须包含字母和数字，长度6-20位', icon: 'none' })
    return
  }
  if (form.value.password !== form.value.confirmPassword) {
    uni.showToast({ title: '两次密码不一致', icon: 'none' })
    return
  }

  loading.value = true
  try {
    // TODO: 调用注册接口
    await new Promise(resolve => setTimeout(resolve, 1000))
    uni.showToast({ title: '注册成功', icon: 'success' })
    setTimeout(() => {
      uni.navigateBack()
    }, 1000)
  } catch (error) {
    uni.showToast({ title: error.message || '注册失败', icon: 'none' })
  } finally {
    loading.value = false
  }
}

const goToLogin = () => {
  uni.navigateBack()
}
</script>

<style scoped lang="scss">
.register-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 100rpx 60rpx;
}

.header {
  text-align: center;
  margin-bottom: 80rpx;
}

.title {
  display: block;
  font-size: 48rpx;
  font-weight: bold;
  color: white;
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

.verify-code {
  display: flex;
  gap: 20rpx;
}

.verify-code .input {
  flex: 1;
}

.code-btn {
  height: 80rpx;
  padding: 0 30rpx;
  background: #667eea;
  border: none;
  border-radius: 10rpx;
  color: white;
  font-size: 24rpx;
}

.code-btn:disabled {
  background: #ccc;
}

.register-btn {
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

.register-btn:disabled {
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
</style>
