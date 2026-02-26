<template>
  <view class="insurance-container">
    <view class="tabs">
      <view
        class="tab-item"
        :class="{ active: activeTab === 'recommend' }"
        @click="switchTab('recommend')"
      >
        智能推荐
      </view>
      <view
        class="tab-item"
        :class="{ active: activeTab === 'all' }"
        @click="switchTab('all')"
      >
        全部产品
      </view>
      <view
        class="tab-item"
        :class="{ active: activeTab === 'my' }"
        @click="switchTab('my')"
      >
        我的保单
      </view>
    </view>

    <!-- 智能推荐 -->
    <view v-if="activeTab === 'recommend'" class="insurance-list">
      <view class="insurance-card" v-for="(item, index) in products" :key="index" @click="viewDetail(item.id)">
        <view class="card-header">
          <view class="company">{{ item.insuranceCompany }}</view>
          <view class="tag">推荐</view>
        </view>
        <view class="card-body">
          <text class="product-name">{{ item.productName }}</text>
          <text class="description">{{ item.description }}</text>
        </view>
        <view class="card-footer">
          <view class="price-info">
            <text class="price">¥{{ item.annualPremium }}</text>
            <text class="unit">/年</text>
          </view>
          <button class="buy-btn" @click.stop="buyInsurance(item.id)">购买</button>
        </view>
      </view>
    </view>

    <!-- 全部产品 -->
    <view v-if="activeTab === 'all'" class="filter-bar">
      <view class="filter-item" :class="{ active: filterType === null }" @click="filterType = null">全部</view>
      <view class="filter-item" :class="{ active: filterType === 0 }" @click="filterType = 0">医疗险</view>
      <view class="filter-item" :class="{ active: filterType === 1 }" @click="filterType = 1">重疾险</view>
      <view class="filter-item" :class="{ active: filterType === 2 }" @click="filterType = 2">意外险</view>
    </view>

    <view v-if="activeTab === 'all'" class="insurance-list">
      <view
        class="insurance-card"
        v-for="(item, index) in filteredProducts"
        :key="index"
        @click="viewDetail(item.id)"
      >
        <view class="card-header">
          <view class="company">{{ item.insuranceCompany }}</view>
        </view>
        <view class="card-body">
          <text class="product-name">{{ item.productName }}</text>
          <text class="description">{{ item.description }}</text>
        </view>
        <view class="card-footer">
          <view class="price-info">
            <text class="price">¥{{ item.annualPremium }}</text>
            <text class="unit">/年</text>
          </view>
          <button class="buy-btn" @click.stop="buyInsurance(item.id)">购买</button>
        </view>
      </view>
    </view>

    <!-- 我的保单 -->
    <view v-if="activeTab === 'my'" class="policy-list">
      <view class="policy-card" v-for="(item, index) in policies" :key="index">
        <view class="policy-header">
          <text class="policy-name">{{ item.productName }}</text>
          <view class="status-tag" :class="'status-' + item.status">
            {{ getStatusText(item.status) }}
          </view>
        </view>
        <view class="policy-body">
          <view class="info-row">
            <text class="label">保单号：</text>
            <text class="value">{{ item.policyNumber }}</text>
          </view>
          <view class="info-row">
            <text class="label">保险公司：</text>
            <text class="value">{{ item.insuranceCompany }}</text>
          </view>
          <view class="info-row">
            <text class="label">保障金额：</text>
            <text class="value">¥{{ item.coverageAmount }}</text>
          </view>
          <view class="info-row">
            <text class="label">年保费：</text>
            <text class="value">¥{{ item.annualPremium }}</text>
          </view>
          <view class="info-row">
            <text class="label">生效日期：</text>
            <text class="value">{{ item.effectiveDate }}</text>
          </view>
          <view class="info-row">
            <text class="label">到期日期：</text>
            <text class="value">{{ item.expiryDate }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const activeTab = ref('recommend')
const filterType = ref(null)
const products = ref([])
const policies = ref([])

const filteredProducts = computed(() => {
  if (filterType.value === null) {
    return products.value
  }
  return products.value.filter(p => p.productType === filterType.value)
})

onMounted(() => {
  if (activeTab.value === 'recommend') {
    loadRecommend()
  } else if (activeTab.value === 'all') {
    loadProducts()
  } else if (activeTab.value === 'my') {
    loadPolicies()
  }
})

const switchTab = (tab) => {
  activeTab.value = tab
  if (tab === 'recommend') {
    loadRecommend()
  } else if (tab === 'all') {
    loadProducts()
  } else if (tab === 'my') {
    loadPolicies()
  }
}

const loadRecommend = async () => {
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/insurance/recommend',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    products.value = response.data.data || []
  } catch (error) {
    console.error('加载推荐保险失败', error)
  }
}

const loadProducts = async () => {
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/insurance/products',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    products.value = response.data.data || []
  } catch (error) {
    console.error('加载保险产品失败', error)
  }
}

const loadPolicies = async () => {
  try {
    const response = await uni.request({
      url: 'http://localhost:8080/api/insurance/my-policies',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    policies.value = response.data.data || []
  } catch (error) {
    console.error('加载我的保单失败', error)
  }
}

const viewDetail = (id) => {
  uni.navigateTo({
    url: `/pages/insurance/detail?id=${id}`
  })
}

const buyInsurance = async (id) => {
  try {
    const response = await uni.request({
      url: `http://localhost:8080/api/insurance/purchase/${id}`,
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + userStore.token
      }
    })
    const url = response.data.data
    if (url) {
      uni.showModal({
        title: '跳转购买',
        content: '即将跳转到保险公司官网进行购买',
        success: (res) => {
          if (res.confirm) {
            // #ifdef H5
            window.open(url, '_blank')
            // #endif
            // #ifndef H5
            uni.showToast({ title: '请在PC端完成购买', icon: 'none' })
            // #endif
          }
        }
      })
    }
  } catch (error) {
    uni.showToast({ title: '获取购买链接失败', icon: 'none' })
  }
}

const getStatusText = (status) => {
  const map = { 0: '待生效', 1: '生效中', 2: '已失效', 3: '已退保' }
  return map[status] || '未知'
}
</script>

<style scoped lang="scss">
.insurance-container {
  min-height: 100vh;
  background: #f5f5f5;
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

.filter-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 15rpx;
  padding: 20rpx;
  background: white;
  margin: 20rpx;
  border-radius: 20rpx;
}

.filter-item {
  padding: 12rpx 30rpx;
  font-size: 26rpx;
  color: #666;
  background: #f5f5f5;
  border-radius: 20rpx;
}

.filter-item.active {
  color: white;
  background: #667eea;
}

.insurance-list {
  padding: 0 20rpx;
}

.insurance-card {
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
}

.company {
  font-size: 28rpx;
  color: #666;
}

.tag {
  padding: 6rpx 16rpx;
  font-size: 20rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 20rpx;
}

.card-body {
  margin-bottom: 20rpx;
}

.product-name {
  display: block;
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 10rpx;
}

.description {
  display: block;
  font-size: 26rpx;
  color: #999;
  line-height: 1.6;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 20rpx;
  border-top: 1rpx solid #f0f0f0;
}

.price-info .price {
  font-size: 40rpx;
  font-weight: bold;
  color: #ff6b6b;
}

.price-info .unit {
  font-size: 24rpx;
  color: #999;
}

.buy-btn {
  padding: 12rpx 40rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 30rpx;
  color: white;
  font-size: 26rpx;
}

.policy-list {
  padding: 0 20rpx;
}

.policy-card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.policy-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.policy-name {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.status-tag {
  padding: 8rpx 20rpx;
  font-size: 22rpx;
  border-radius: 20rpx;
}

.status-0 {
  background: #fff7e6;
  color: #fa8c16;
}

.status-1 {
  background: #f6ffed;
  color: #52c41a;
}

.status-2 {
  background: #fff1f0;
  color: #ff4d4f;
}

.status-3 {
  background: #f0f0f0;
  color: #999;
}

.policy-body {
  margin-bottom: 10rpx;
}

.info-row {
  display: flex;
  margin-bottom: 12rpx;
}

.info-row .label {
  font-size: 26rpx;
  color: #666;
  width: 160rpx;
}

.info-row .value {
  font-size: 26rpx;
  color: #333;
  flex: 1;
}
</style>
