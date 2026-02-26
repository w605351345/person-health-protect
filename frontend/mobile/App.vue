<script setup>
import { onLaunch, onShow, onHide } from '@dcloudio/uni-app'

onLaunch(() => {
  console.log('App Launch')
})

onShow(() => {
  console.log('App Show')
})

onHide(() => {
  console.log('App Hide')
})
</script>

<style>
/* 全局样式 */
page {
  font-family: 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
  font-size: 28rpx;
  line-height: 1.6;
  color: #333;
  background-color: #f5f5f5;
}

/* 去除默认样式 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

/* 文本不换行 */
.text-nowrap {
  white-space: nowrap;
}

/* 文本溢出省略 */
.text-ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 两行省略 */
.text-ellipsis-2 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
