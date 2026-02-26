<template>
  <view class="container">
    <!-- 顶部用户信息卡片 -->
    <view class="user-card">
      <view class="user-info">
        <image class="avatar" :src="userInfo.avatar || '/static/default-avatar.png'" mode="aspectFill"></image>
        <view class="info">
          <text class="name">{{ userInfo.nickname || '未登录' }}</text>
          <text class="phone" v-if="userInfo.phone">{{ maskPhone(userInfo.phone) }}</text>
        </view>
      </view>
      <view class="quick-actions">
        <view class="action-item" @click="navigateTo('/pages/profile/profile')">
          <text class="icon">📋</text>
          <text class="label">完善档案</text>
        </view>
        <view class="action-item" @click="navigateTo('/pages/health/record')">
          <text class="icon">💪</text>
          <text class="label">录入数据</text>
        </view>
        <view class="action-item" @click="navigateTo('/pages/insurance/recommend')">
          <text class="icon">🛡️</text>
          <text class="label">保险推荐</text>
        </view>
      </view>
    </view>

    <!-- 健康概览 -->
    <view class="section">
      <view class="section-header">
        <text class="title">健康概览</text>
        <text class="more" @click="navigateTo('/pages/health/health')">更多 ></text>
      </view>
      <view class="health-overview">
        <view class="overview-item">
          <text class="value">{{ latestHealth.weight || '--' }}</text>
          <text class="unit">kg</text>
          <text class="label">体重</text>
        </view>
        <view class="overview-item">
          <text class="value">{{ latestHealth.systolicPressure || '--' }}/{{ latestHealth.diastolicPressure || '--' }}</text>
          <text class="unit">mmHg</text>
          <text class="label">血压</text>
        </view>
        <view class="overview-item">
          <text class="value">{{ latestHealth.bloodSugar || '--' }}</text>
          <text class="unit">mmol/L</text>
          <text class="label">血糖</text>
        </view>
      </view>
    </view>

    <!-- 快捷功能 -->
    <view class="section">
      <view class="section-header">
        <text class="title">快捷功能</text>
      </view>
      <view class="functions">
        <view class="function-item" @click="navigateTo('/pages/health/trends')">
          <text class="icon">📈</text>
          <text class="label">健康趋势</text>
        </view>
        <view class="function-item" @click="navigateTo('/pages/medical/medical')">
          <text class="icon">🏥</text>
          <text class="label">医疗记录</text>
        </view>
        <view class="function-item" @click="navigateTo('/pages/insurance/products')">
          <text class="icon">🛡️</text>
          <text class="label">保险产品</text>
        </view>
        <view class="function-item" @click="navigateTo('/pages/insurance/mypolicies')">
          <text class="icon">📄</text>
          <text class="label">我的保单</text>
        </view>
        <view class="function-item" @click="syncMedicalRecords">
          <text class="icon">🔄</text>
          <text class="label">同步记录</text>
        </view>
        <view class="function-item" @click="generateReport">
          <text class="icon">📊</text>
          <text class="label">健康报告</text>
        </view>
      </view>
    </view>

    <!-- 保险推荐 -->
    <view class="section" v-if="recommendInsurance.length > 0">
      <view class="section-header">
        <text class="title">为您推荐</text>
        <text class="more" @click="navigateTo('/pages/insurance/recommend')">更多 ></text>
      </view>
      <view class="insurance-list">
        <view class="insurance-item" v-for="item in recommendInsurance" :key="item.id" @click="viewInsurance(item.id)">
          <view class="insurance-info">
            <text class="name">{{ item.productName }}</text>
            <text class="company">{{ item.insuranceCompany }}</text>
          </view>
          <view class="insurance-price">
            <text class="price">¥{{ item.annualPremium }}/年</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const userInfo = ref({})
const latestHealth = ref({})
const recommendInsurance = ref([])

onMounted(() => {
  loadUserInfo()
  loadLatestHealth()
  loadRecommendInsurance()
})

// 加载用户信息
const loadUserInfo = async () => {
  try {
    const data = await userStore.getUserInfo()
    userInfo.value = data
  } catch (error) {
    console.error('加载用户信息失败', error)
  }
}

// 加载最新健康数据
const loadLatestHealth = async () => {
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/health/records',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    if (response.data.data && response.data.data.length > 0) {
      latestHealth.value = response.data.data[0]
    }
  } catch (error) {
    console.error('加载健康数据失败', error)
  }
}

// 加载推荐保险
const loadRecommendInsurance = async () => {
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/insurance/recommend',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    if (response.data.data) {
      recommendInsurance.value = response.data.data.slice(0, 3)
    }
  } catch (error) {
    console.error('加载推荐保险失败', error)
  }
}

// 同步医疗记录
const syncMedicalRecords = async () => {
  uni.showLoading({ title: '同步中...' })
  try {
    await uni.request({
      url: 'http://localhost:8080/api/medical/sync',
      method: 'POST',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    uni.showToast({ title: '同步成功', icon: 'success' })
  } catch (error) {
    uni.showToast({ title: '同步失败', icon: 'error' })
  } finally {
    uni.hideLoading()
  }
}

// 生成健康报告
const generateReport = async () => {
  uni.showLoading({ title: '生成中...' })
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/health/report',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    uni.showModal({
      title: '健康报告',
      content: response.data.data || '报告生成成功',
      showCancel: false
    })
  } catch (error) {
    uni.showToast({ title: '生成失败', icon: 'error' })
  } finally {
    uni.hideLoading()
  }
}

// 跳转页面
const navigateTo = (url) => {
  uni.navigateTo({ url })
}

// 查看保险详情
const viewInsurance = (id) => {
  uni.navigateTo({
    url: `/pages/insurance/detail?id=${id}`
  })
}

// 手机号脱敏
const maskPhone = (phone) => {
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}
</script>

<style scoped>
.container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding-bottom: 20px;
}

.user-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 30px 20px;
  color: white;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.5);
}

.info {
  margin-left: 15px;
  flex: 1;
}

.name {
  display: block;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.phone {
  display: block;
  font-size: 14px;
  opacity: 0.9;
}

.quick-actions {
  display: flex;
  justify-content: space-around;
}

.action-item {
  text-align: center;
}

.action-item .icon {
  font-size: 24px;
  display: block;
  margin-bottom: 5px;
}

.action-item .label {
  font-size: 12px;
}

.section {
  background: white;
  margin: 10px;
  padding: 15px;
  border-radius: 10px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.title {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.more {
  font-size: 14px;
  color: #999;
}

.health-overview {
  display: flex;
  justify-content: space-around;
}

.overview-item {
  text-align: center;
}

.overview-item .value {
  display: block;
  font-size: 20px;
  font-weight: bold;
  color: #333;
}

.overview-item .unit {
  font-size: 12px;
  color: #999;
}

.overview-item .label {
  display: block;
  font-size: 12px;
  color: #666;
  margin-top: 5px;
}

.functions {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.function-item {
  text-align: center;
  padding: 15px 0;
}

.function-item .icon {
  font-size: 28px;
  display: block;
  margin-bottom: 8px;
}

.function-item .label {
  font-size: 13px;
  color: #666;
}

.insurance-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.insurance-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: #f9f9f9;
  border-radius: 8px;
}

.insurance-info {
  flex: 1;
}

.insurance-info .name {
  display: block;
  font-size: 15px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.insurance-info .company {
  font-size: 13px;
  color: #999;
}

.insurance-price .price {
  font-size: 16px;
  font-weight: bold;
  color: #ff6b6b;
}
</style>
