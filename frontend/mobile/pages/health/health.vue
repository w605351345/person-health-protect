<template>
  <view class="health-container">
    <view class="stats-card">
      <view class="stat-item">
        <text class="stat-value">{{ latest.weight || '--' }}</text>
        <text class="stat-label">体重(kg)</text>
      </view>
      <view class="stat-item">
        <text class="stat-value">{{ latest.systolicPressure || '--' }}/{{ latest.diastolicPressure || '--' }}</text>
        <text class="stat-label">血压</text>
      </view>
      <view class="stat-item">
        <text class="stat-value">{{ latest.bloodSugar || '--' }}</text>
        <text class="stat-label">血糖</text>
      </view>
    </view>

    <view class="chart-card">
      <view class="card-header">
        <text class="title">健康趋势</text>
        <view class="tabs">
          <text class="tab" :class="{ active: activeTab === 'weight' }" @click="activeTab = 'weight'">体重</text>
          <text class="tab" :class="{ active: activeTab === 'pressure' }" @click="activeTab = 'pressure'">血压</text>
          <text class="tab" :class="{ active: activeTab === 'sugar' }" @click="activeTab = 'sugar'">血糖</text>
        </view>
      </view>
      <!-- 这里应该显示图表，简化起见显示列表 -->
      <scroll-view scroll-y class="chart-list">
        <view class="chart-item" v-for="(item, index) in healthRecords" :key="index">
          <view class="item-left">
            <text class="date">{{ formatDate(item.recordTime) }}</text>
          </view>
          <view class="item-right">
            <text v-if="activeTab === 'weight'" class="value">{{ item.weight }} kg</text>
            <text v-if="activeTab === 'pressure'" class="value">{{ item.systolicPressure }}/{{ item.diastolicPressure }}</text>
            <text v-if="activeTab === 'sugar'" class="value">{{ item.bloodSugar }}</text>
          </view>
        </view>
      </scroll-view>
    </view>

    <view class="list-card">
      <view class="card-header">
        <text class="title">健康记录</text>
        <text class="more" @click="loadMore">更多 ></text>
      </view>
      <view class="record-list">
        <view class="record-item" v-for="(item, index) in healthRecords" :key="index">
          <view class="record-date">{{ formatDate(item.recordTime) }}</view>
          <view class="record-data">
            <view class="data-row" v-if="item.weight">
              <text class="label">体重：</text>
              <text class="value">{{ item.weight }} kg</text>
            </view>
            <view class="data-row" v-if="item.systolicPressure">
              <text class="label">血压：</text>
              <text class="value">{{ item.systolicPressure }}/{{ item.diastolicPressure }} mmHg</text>
            </view>
            <view class="data-row" v-if="item.bloodSugar">
              <text class="label">血糖：</text>
              <text class="value">{{ item.bloodSugar }} mmol/L</text>
            </view>
            <view class="data-row" v-if="item.heartRate">
              <text class="label">心率：</text>
              <text class="value">{{ item.heartRate }} 次/分</text>
            </view>
          </view>
          <view class="record-remark" v-if="item.remark">{{ item.remark }}</view>
        </view>
      </view>
    </view>

    <button class="add-btn" @click="addRecord">+ 录入数据</button>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const activeTab = ref('weight')
const healthRecords = ref([])

const latest = computed(() => {
  return healthRecords.value[0] || {}
})

onMounted(() => {
  loadHealthRecords()
})

const loadHealthRecords = async () => {
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/health/records',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    healthRecords.value = response.data.data || []
  } catch (error) {
    console.error('加载健康数据失败', error)
  }
}

const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  const month = d.getMonth() + 1
  const day = d.getDate()
  return `${month}月${day}日`
}

const addRecord = () => {
  uni.navigateTo({ url: '/pages/health/record' })
}

const loadMore = () => {
  uni.showToast({ title: '查看更多', icon: 'none' })
}
</script>

<style scoped lang="scss">
.health-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 120rpx;
}

.stats-card {
  display: flex;
  justify-content: space-around;
  background: white;
  padding: 40rpx 20rpx;
  margin: 20rpx;
  border-radius: 20rpx;
}

.stat-item {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 40rpx;
  font-weight: bold;
  color: #667eea;
  margin-bottom: 10rpx;
}

.stat-label {
  font-size: 24rpx;
  color: #666;
}

.chart-card,
.list-card {
  background: white;
  margin: 20rpx;
  border-radius: 20rpx;
  overflow: hidden;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.tabs {
  display: flex;
  gap: 20rpx;
}

.tab {
  padding: 10rpx 20rpx;
  font-size: 24rpx;
  color: #666;
  border-radius: 20rpx;
  background: #f5f5f5;
}

.tab.active {
  color: white;
  background: #667eea;
}

.more {
  font-size: 24rpx;
  color: #999;
}

.chart-list {
  height: 400rpx;
}

.chart-item {
  display: flex;
  justify-content: space-between;
  padding: 25rpx 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.item-left .date {
  font-size: 24rpx;
  color: #666;
}

.item-right .value {
  font-size: 28rpx;
  color: #667eea;
  font-weight: bold;
}

.record-list {
  max-height: 800rpx;
  overflow-y: auto;
}

.record-item {
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.record-date {
  font-size: 24rpx;
  color: #999;
  margin-bottom: 15rpx;
}

.record-data {
  background: #f9f9f9;
  padding: 20rpx;
  border-radius: 10rpx;
}

.data-row {
  display: flex;
  margin-bottom: 10rpx;
}

.data-row:last-child {
  margin-bottom: 0;
}

.data-row .label {
  font-size: 26rpx;
  color: #666;
  width: 120rpx;
}

.data-row .value {
  font-size: 26rpx;
  color: #333;
  flex: 1;
}

.record-remark {
  font-size: 24rpx;
  color: #666;
  margin-top: 15rpx;
  padding: 15rpx;
  background: #fff8e1;
  border-radius: 8rpx;
}

.add-btn {
  position: fixed;
  bottom: 40rpx;
  left: 50%;
  transform: translateX(-50%);
  width: 200rpx;
  height: 80rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 40rpx;
  color: white;
  font-size: 28rpx;
  font-weight: bold;
  box-shadow: 0 4rpx 20rpx rgba(102, 126, 234, 0.4);
}
</style>
