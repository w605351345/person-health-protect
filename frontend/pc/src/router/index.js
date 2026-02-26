import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/dashboard'
      },
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/profile/Index.vue'),
        meta: { title: '个人档案', icon: 'User' }
      },
      {
        path: 'health',
        name: 'Health',
        component: () => import('@/views/health/Index.vue'),
        meta: { title: '健康数据', icon: 'TrendCharts' }
      },
      {
        path: 'health/record',
        name: 'HealthRecord',
        component: () => import('@/views/health/Record.vue'),
        meta: { title: '录入数据', icon: 'Plus' }
      },
      {
        path: 'health/trends',
        name: 'HealthTrends',
        component: () => import('@/views/health/Trends.vue'),
        meta: { title: '健康趋势', icon: 'DataLine' }
      },
      {
        path: 'medical',
        name: 'Medical',
        component: () => import('@/views/medical/Index.vue'),
        meta: { title: '医疗记录', icon: 'Hospital' }
      },
      {
        path: 'insurance',
        name: 'Insurance',
        component: () => import('@/views/insurance/Index.vue'),
        meta: { title: '保险服务', icon: 'Shield' }
      },
      {
        path: 'insurance/recommend',
        name: 'InsuranceRecommend',
        component: () => import('@/views/insurance/Recommend.vue'),
        meta: { title: '智能推荐', icon: 'Star' }
      },
      {
        path: 'insurance/products',
        name: 'InsuranceProducts',
        component: () => import('@/views/insurance/Products.vue'),
        meta: { title: '保险产品', icon: 'List' }
      },
      {
        path: 'insurance/mypolicies',
        name: 'MyPolicies',
        component: () => import('@/views/insurance/MyPolicies.vue'),
        meta: { title: '我的保单', icon: 'Document' }
      },
      {
        path: 'settings',
        name: 'Settings',
        component: () => import('@/views/Settings.vue'),
        meta: { title: '设置', icon: 'Setting' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  // 设置页面标题
  if (to.meta.title) {
    document.title = `${to.meta.title} - 家庭健康档案`
  }

  // 检查是否需要登录
  if (to.meta.requiresAuth && !userStore.token) {
    next('/login')
  } else {
    next()
  }
})

export default router
