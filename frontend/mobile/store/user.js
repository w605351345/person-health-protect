import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref('')
  const userInfo = ref(null)

  // 登录
  const login = async (credentials) => {
    const response = await uni.request({
      url: 'http://localhost:8080/api/auth/login',
      method: 'POST',
      data: credentials
    })

    if (response.data.code === 200) {
      token.value = response.data.data.accessToken
      userInfo.value = await getUserInfo()

      // 保存到本地存储
      uni.setStorageSync('token', token.value)
      uni.setStorageSync('userInfo', userInfo.value)

      return true
    } else {
      throw new Error(response.data.message || '登录失败')
    }
  }

  // 登出
  const logout = async () => {
    try {
      await uni.request({
        url: 'http://localhost:8080/api/auth/logout',
        method: 'POST',
        header: {
          'Authorization': 'Bearer ' + token.value
        }
      })
    } catch (error) {
      console.error('登出失败', error)
    } finally {
      token.value = ''
      userInfo.value = null

      // 清除本地存储
      uni.removeStorageSync('token')
      uni.removeStorageSync('userInfo')

      // 跳转到登录页
      uni.reLaunch({ url: '/pages/login/login' })
    }
  }

  // 获取用户信息
  const getUserInfo = async () => {
    const response = await uni.request({
      url: 'http://localhost:8080/api/user/profile',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + token.value
      }
    })

    if (response.data.code === 200) {
      return response.data.data
    } else {
      throw new Error(response.data.message || '获取用户信息失败')
    }
  }

  // 获取用户档案
  const getUserProfile = async () => {
    const response = await uni.request({
      url: 'http://localhost:8080/api/user/profile-detail',
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + token.value
      }
    })

    if (response.data.code === 200) {
      return response.data.data
    } else {
      throw new Error(response.data.message || '获取用户档案失败')
    }
  }

  // 初始化：从本地存储恢复
  const init = () => {
    const savedToken = uni.getStorageSync('token')
    const savedUserInfo = uni.getStorageSync('userInfo')

    if (savedToken) {
      token.value = savedToken
    }

    if (savedUserInfo) {
      userInfo.value = savedUserInfo
    }
  }

  // 初始化
  init()

  return {
    token,
    userInfo,
    login,
    logout,
    getUserInfo,
    getUserProfile
  }
}, {
  persist: true
})
