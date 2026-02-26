<template>
  <view class="medical-container">
    <view class="tabs">
      <view
        class="tab-item"
        :class="{ active: activeTab === 'all' }"
        @click="activeTab = 'all'"
      >
        全部
      </view>
      <view
        class="tab-item"
        :class="{ active: activeTab === 'insurance' }"
        @click="activeTab = 'insurance'"
      >
        医保
      </view>
      <view
        class="tab-item"
        :class="{ active: activeTab === 'hospital' }"
        @click="activeTab = 'hospital'"
      >
        医院
      </view>
    </view>

    <view class="visit-list">
      <view class="visit-card" v-for="(item, index) in visits" :key="index">
        <view class="card-header">
          <text class="hospital-name">{{ item.hospitalName }}</text>
          <text class="visit-date">{{ formatDate(item.visitDate) }}</text>
        </view>
        <view class="card-body">
          <view class="info-row">
            <text class="label">科室：</text>
            <text class="value">{{ item.department || '-' }}</text>
          </view>
          <view class="info-row">
            <text class="label">医生：</text>
            <text class="value">{{ item.doctorName || '-' }}</text>
          </view>
          <view class="info-row">
            <text class="label">诊断：</text>
            <text class="value">{{ item.diagnosis || '-' }}</text>
          </view>
          <view class="info-row">
            <text class="label">费用：</text>
            <text class="value">¥{{ item.medicalExpense || '0.00' }}</text>
          </view>
          <view class="info-row" v-if="item.insuranceReimbursement">
            <text class="label">报销：</text>
            <text class="value highlight">¥{{ item.insuranceReimbursement }}</text>
          </view>
          <view class="info-row" v-if="item.prescription">
            <text class="label">处方：</text>
            <text class="value">{{ item.prescription }}</text>
          </view>
        </view>
        <view class="card-footer">
          <text class="source-tag" :class="'source-' + item.dataSource">
            {{ getSourceText(item.dataSource) }}
          </text>
        </view>
      </view>
    </view>

    <button class="sync-btn" @click="syncRecords">同步记录</button>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const activeTab = ref('all')
const visits = ref([])

onMounted(() => {
  loadVisits()
})

const loadVisits = async () => {
  try {
    let url = 'http://localhost:8080/api/medical/visits'
    if (activeTab.value === 'insurance') {
      url = 'http://localhost:8080/api/medical/visits/insurance'
    } else if (activeTab.value === 'hospital') {
      url = 'http://localhost:8080/api/medical/visits/hospital'
    }

    const response = await uni.request({
      url,
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    visits.value = response.data.data || []
  } catch (error) {
    console.error('加载就医记录失败', error)
  }
}

const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  const year = d.getFullYear()
  const month = d.getMonth() + 1
  const day = d.getDate()
  return `${year}-${month}-${day}`
}

const getSourceText = (source) => {
  const map = { 0: '医保', 1: '医院', 2: '用户录入' }
  return map[source] || '未知'
}

const syncRecords = async () => {
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
    loadVisits()
  } catch (error) {
    uni.showToast({ title: '同步失败', icon: 'none' })
  } finally {
    uni.hideLoading()
  }
}

// 监听 Tab 切换
watch(activeTab, () => {
  loadVisits()
})
</script>

<script>
import { watch } from 'vue'
export default { setup }
</script>

<style scoped lang="scss">
.medical-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 120rpx;
}

.tabs {
  display: flex;
  background: white;
  padding: 20rpx;
  margin: 20rpx;
  border-radius: 20rpx;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 20rpx;
  font-size: 28rpx;
  color: #666;
  border-radius: 10rpx;
}

.tab-item.active {
  color: #667eea;
  background: rgba(102, 126, 234, 0.1);
  font-weight: bold;
}

.visit-list {
  padding: 0 20rpx;
}

.visit-card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.hospital-name {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.visit-date {
  font-size: 24rpx;
  color: #999;
}

.card-body {
  margin-bottom: 20rpx;
}

.info-row {
  display: flex;
  margin-bottom: 15rpx;
}

.info-row .label {
  font-size: 26rpx;
  color: #666;
  width: 100rpx;
}

.info-row .value {
  font-size: 26rpx;
  color: #333;
  flex: 1;
}

.info-row .value.highlight {
  color: #52c41a;
  font-weight: bold;
}

.card-footer {
  display: flex;
  justify-content: flex-end;
  padding-top: 15rpx;
  border-top: 1rpx solid #f0f0f0;
}

.source-tag {
  padding: 8rpx 20rpx;
  font-size: 22rpx;
  border-radius: 20rpx;
}

.source-0 {
  background: #e6f7ff;
  color: #1890ff;
}

.source-1 {
  background: #f6ffed;
  color: #52c41a;
}

.source-2 {
  background: #fff7e6;
  color: #fa8c16;
}

.sync-btn {
  position: fixed;
  bottom: 40rpx;
  left: 50%;
  transform: translateX(-50%);
  width: 300rpx;
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
