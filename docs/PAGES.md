# 页面设计文档

## 📋 目录

- [设计规范](#设计规范)
- [移动端页面](#移动端页面)
- [PC端页面](#pc端页面)
- [组件库](#组件库)

---

## 设计规范

### 色彩系统

#### 主色调

```scss
// 渐变紫
$primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
$primary-color: #667eea;
$primary-dark: #5568d3;

// 辅助色
$success-color: #52c41a;
$warning-color: #fa8c16;
$error-color: #ff4d4f;
$info-color: #1890ff;

// 中性色
$text-primary: #333333;
$text-secondary: #666666;
$text-tertiary: #999999;
$border-color: #e0e0e0;
$background-color: #f5f5f5;
```

#### 功能色

| 颜色 | 用途 | Hex |
|------|------|-----|
| 绿色 | 成功、正常 | #52c41a |
| 橙色 | 警告 | #fa8c16 |
| 红色 | 错误、高危 | #ff4d4f |
| 蓝色 | 信息 | #1890ff |
| 紫色 | 主色调 | #667eea |

### 字体系统

```scss
// 字体大小
$font-size-xs: 20rpx;      // 10px
$font-size-sm: 24rpx;      // 12px
$font-size-base: 28rpx;    // 14px
$font-size-md: 32rpx;     // 16px
$font-size-lg: 36rpx;     // 18px
$font-size-xl: 40rpx;     // 20px
$font-size-xxl: 48rpx;    // 24px

// 字体粗细
$font-weight-normal: 400;
$font-weight-medium: 500;
$font-weight-bold: 700;
```

### 间距系统

```scss
// 间距（8px 基准）
$spacing-xs: 8rpx;       // 4px
$spacing-sm: 16rpx;      // 8px
$spacing-md: 20rpx;      // 10px
$spacing-lg: 30rpx;      // 15px
$spacing-xl: 40rpx;      // 20px
$spacing-xxl: 60rpx;     // 30px
```

### 圆角系统

```scss
// 圆角
$radius-sm: 8rpx;        // 4px
$radius-md: 10rpx;       // 5px
$radius-lg: 20rpx;       // 10px
$radius-xl: 30rpx;       // 15px
$radius-round: 50%;       // 圆形
```

### 阴影系统

```scss
// 阴影
$shadow-sm: 0 2rpx 8rpx rgba(0, 0, 0, 0.08);
$shadow-md: 0 4rpx 16rpx rgba(0, 0, 0, 0.12);
$shadow-lg: 0 8rpx 24rpx rgba(0, 0, 0, 0.16);
$shadow-colored: 0 4rpx 20rpx rgba(102, 126, 234, 0.4);
```

---

## 移动端页面

### 1. 登录页面 (login.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│                                 │
│      [Logo + Slogan]            │  顶部：品牌展示
│                                 │
│  ┌───────────────────────────┐  │
│  │ ┃ 手机号                 │  │  表单区域
│  └───────────────────────────┘  │
│  ┌───────────────────────────┐  │
│  │ ┃ 密码                   │  │
│  └───────────────────────────┘  │
│                                 │
│  [     登录按钮      ]          │  登录按钮
│                                 │
│  还没有账号？立即注册            │  注册入口
│                                 │
│  ┌───────────────────────────┐  │  测试账号提示
│  │ 测试账号：                │  │
│  │ 手机号：13800138000       │  │
│  │ 密码：123456             │  │
│  └───────────────────────────┘  │
│                                 │
└─────────────────────────────────┘
```

#### 样式代码

```scss
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 100rpx 60rpx;
}

.title {
  font-size: 48rpx;
  font-weight: bold;
  color: white;
  margin-bottom: 20rpx;
}

.input {
  height: 80rpx;
  padding: 0 20rpx;
  border: 1rpx solid #e0e0e0;
  border-radius: 10rpx;
  font-size: 28rpx;
  background: #f9f9f9;
}

.login-btn {
  height: 88rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 10rpx;
  color: white;
  font-size: 32rpx;
  font-weight: bold;
  margin-top: 40rpx;
}
```

#### 交互状态

| 元素 | 默认 | 聚焦 | 禁用 |
|------|------|------|------|
| 输入框 | 浅灰背景 | 紫色边框 | 透明度 0.6 |
| 登录按钮 | 渐变紫 | 渐变紫（加深） | 透明度 0.6 |

---

### 2. 注册页面 (register.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│                                 │
│      注册账号                    │  顶部标题
│                                 │
│  ┌───────────────────────────┐  │
│  │ ┃ 手机号                 │  │  手机号
│  └───────────────────────────┘  │
│  ┌───────────────────┐ ┌────┐ │
│  │ ┃ 验证码          │ │60s │ │  验证码
│  └───────────────────┘ └────┘ │
│  ┌───────────────────────────┐  │
│  │ ┃ 密码                   │  │  密码
│  └───────────────────────────┘  │
│  ┌───────────────────────────┐  │
│  │ ┃ 确认密码               │  │  确认密码
│  └───────────────────────────┘  │
│                                 │
│  [     注册按钮      ]          │  注册按钮
│                                 │
│  已有账号？立即登录              │  登录入口
│                                 │
└─────────────────────────────────┘
```

#### 验证码按钮样式

```scss
.code-btn {
  height: 80rpx;
  padding: 0 30rpx;
  background: #667eea;
  border-radius: 10rpx;
  color: white;
  font-size: 24rpx;

  &:disabled {
    background: #ccc;
  }
}
```

---

### 3. 首页 (index.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│  [头像] 张三        138***8000 │  用户卡片
│  [完善档案] [录入数据] [保险]  │  快捷操作
├─────────────────────────────────┤
│  健康概览        更多 >       │
│  ┌─────┬─────┬─────┐          │
│  │70.5 │120/80│ 5.4│         │  健康指标
│  │ kg  │mmHg │mmol│         │
│  │体重 │血压 │血糖│         │
│  └─────┴─────┴─────┘          │
├─────────────────────────────────┤
│  快捷功能                       │
│  ┌───┬───┬───┐              │
│  │📈 │🏥 │🛡️│              │  功能入口
│  │趋势│医疗│保险│              │
│  ├───┼───┼───┤              │
│  │🔄 │📊 │➕ │              │
│  │同步│报告│录入│              │
│  └───┴───┴───┘              │
├─────────────────────────────────┤
│  为您推荐        更多 >       │
│  ┌─────────────────────────┐  │
│  │ 百万医疗险         ¥365 │  │  保险推荐
│  │ 平安保险        [购买]  │  │
│  └─────────────────────────┘  │
└─────────────────────────────────┘
```

#### 用户卡片样式

```scss
.user-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 30px 20px;
  color: white;
  border-radius: 20rpx;
  margin: 20rpx;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.5);
}
```

---

### 4. 个人档案页面 (profile.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│         [头像]                 │  顶部头像
│           张三                 │
│         138***8000             │
├─────────────────────────────────┤
│  基本信息                      │  │
│  姓名：张三                    │  │  基本信息
│  性别：男                      │  │
│  年龄：34岁                    │  │
│  身份证：110101********1234     │  │
│  民族：汉族                    │  │
│  出生日期：1990-01-01          │  │
│  地址：北京市朝阳区建国路88号    │  │
├─────────────────────────────────┤
│  身份证照片                    │  │
│  ┌──────────┐ ┌──────────┐    │  │  照片上传
│  │  正面    │ │  背面    │    │  │
│  │  [图片]  │ │  [图片]  │    │  │
│  └──────────┘ └──────────┘    │  │
├─────────────────────────────────┤
│  紧急联系人                    │  │
│  姓名：李四                     │  │  联系人
│  电话：139***9000              │  │
├─────────────────────────────────┤
│       [编辑档案]               │  编辑按钮
└─────────────────────────────────┘
```

#### 信息卡片样式

```scss
.info-card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin: 20rpx;
}

.info-title {
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
```

---

### 5. 健康数据页面 (health.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│  ┌─────┬─────┬─────┐          │
│  │70.5 │120/80│ 5.4│         │  健康指标卡片
│  │ kg  │mmHg │mmol│         │
│  │体重 │血压 │血糖│         │
│  └─────┴─────┴─────┘          │
├─────────────────────────────────┤
│  健康趋势   [体重][血压][血糖]  │  标签切换
│  ┌─────────────────────────┐    │
│  │ 2月25日    70.5 kg    │    │  趋势列表
│  │ 2月24日    70.2 kg    │    │
│  │ 2月23日    70.8 kg    │    │
│  │ ...                   │    │
│  └─────────────────────────┘    │
├─────────────────────────────────┤
│  健康记录            更多 >    │
│  ┌─────────────────────────┐    │
│  │ 2月25日                │    │  记录列表
│  │ 体重：70.5 kg          │    │
│  │ 血压：120/80 mmHg      │    │
│  │ 血糖：5.4 mmol/L       │    │
│  │ 心率：75 次/分         │    │
│  │ 备注：正常              │    │
│  └─────────────────────────┘    │
│                         [+ 录入数据] │ 悬浮按钮
└─────────────────────────────────┘
```

#### 统计卡片样式

```scss
.stats-card {
  display: flex;
  justify-content: space-around;
  background: white;
  padding: 40rpx 20rpx;
  margin: 20rpx;
  border-radius: 20rpx;
}

.stat-value {
  font-size: 40rpx;
  font-weight: bold;
  color: #667eea;
}

.stat-label {
  font-size: 24rpx;
  color: #666;
}
```

---

### 6. 医疗记录页面 (medical.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│  [全部] [医保] [医院]         │  Tab 切换
├─────────────────────────────────┤
│  ┌─────────────────────────┐    │
│  │ 北京协和医院      2月11日 │  │  就医记录卡片
│  ├─────────────────────────┤  │
│  │ 科室：内科             │  │
│  │ 医生：王医生           │  │
│  │ 诊断：轻微感冒，咽喉红肿│  │
│  │ 费用：156.50元        │  │
│  │ 报销：45.00元         │  │
│  │ 处方：感冒灵颗粒 3盒   │  │
│  ├─────────────────────────┤  │
│  │        [医保]           │  │  来源标签
│  └─────────────────────────┘    │
│  ┌─────────────────────────┐    │
│  │ 北京朝阳医院      12月26日│  │
│  │ ...                   │  │
│  └─────────────────────────┘    │
│                         [同步记录] │ 悬浮按钮
└─────────────────────────────────┘
```

#### 来源标签样式

```scss
.source-tag {
  padding: 8rpx 20rpx;
  font-size: 22rpx;
  border-radius: 20rpx;

  &.source-0 {
    background: #e6f7ff;
    color: #1890ff;  // 医保
  }

  &.source-1 {
    background: #f6ffed;
    color: #52c41a;  // 医院
  }

  &.source-2 {
    background: #fff7e6;
    color: #fa8c16;  // 用户录入
  }
}
```

---

### 7. 保险服务页面 (insurance.vue)

#### 页面布局

```
┌─────────────────────────────────┐
│  [智能推荐] [全部产品] [我的] │  Tab 切换
├─────────────────────────────────┤
│  [全部][医疗险][重疾险][意外险] │  筛选（仅全部产品页）
├─────────────────────────────────┤
│  ┌─────────────────────────┐    │
│  │ 平安保险        [推荐] │  │  保险卡片
│  ├─────────────────────────┤  │
│  │ 百万医疗险              │  │
│  │ 高额医疗费用保障        │  │
│  ├─────────────────────────┤  │
│  │ ¥365 /年      [购买]  │  │
│  └─────────────────────────┘    │
│  ┌─────────────────────────┐    │
│  │ 中国人寿               │  │
│  │ 重疾无忧               │  │
│  │ 重大疾病保障           │  │
│  ├─────────────────────────┤  │
│  │ ¥5000 /年     [购买] │  │
│  └─────────────────────────┘    │
└─────────────────────────────────┘
```

#### 保险卡片样式

```scss
.insurance-card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.tag {
  padding: 6rpx 16rpx;
  font-size: 20rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 20rpx;
}

.price {
  font-size: 40rpx;
  font-weight: bold;
  color: #ff6b6b;
}

.buy-btn {
  padding: 12rpx 40rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 30rpx;
  color: white;
  font-size: 26rpx;
}
```

---

## PC端页面

### 1. 登录页面 (Login.vue)

#### 页面布局

```
┌───────────────────────────────────────────────────────┐
│                                                       │
│                    [Logo]                            │
│                家庭健康档案系统                        │
│                                                       │
│  ┌───────────────────────────────────────────────┐    │
│  │                                              │    │
│  │        手机号：[_______________]             │    │  表单卡片
│  │                                              │    │  居中显示
│  │        密  码：[_______________]             │    │
│  │                                              │    │
│  │         [         登录         ]             │    │
│  │                                              │    │
│  │        还没有账号？立即注册                   │    │
│  │                                              │    │
│  └───────────────────────────────────────────────┘    │
│                                                       │
│                    测试账号：                          │
│              手机号：13800138000                        │
│              密码：123456                              │
│                                                       │
└───────────────────────────────────────────────────────┘
```

#### 样式规范

```scss
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-card {
  width: 400px;
  background: white;
  border-radius: 20px;
  padding: 60px 40px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
}

.el-input {
  height: 50px;
  margin-bottom: 20px;
}

.el-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: bold;
}
```

---

## 组件库

### 1. 按钮组件

#### 主按钮

```vue
<el-button type="primary" size="large">
  主要操作
</el-button>
```

#### 次要按钮

```vue
<el-button size="large">
  次要操作
</el-button>
```

#### 危险按钮

```vue
<el-button type="danger" size="large">
  危险操作
</el-button>
```

### 2. 卡片组件

#### 基础卡片

```vue
<el-card shadow="hover">
  卡片内容
</el-card>
```

#### 带标题卡片

```vue
<el-card>
  <template #header>
    <span>卡片标题</span>
  </template>
  卡片内容
</el-card>
```

### 3. 表格组件

```vue
<el-table :data="tableData" stripe>
  <el-table-column prop="name" label="姓名" />
  <el-table-column prop="age" label="年龄" />
</el-table>
```

### 4. 表单组件

```vue
<el-form :model="form" label-width="100px">
  <el-form-item label="手机号">
    <el-input v-model="form.phone" />
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submit">提交</el-button>
  </el-form-item>
</el-form>
```

---

## 响应式设计

### 断点规范

| 设备 | 宽度范围 | 字体基准 |
|------|---------|---------|
| 移动端 | < 768px | 28rpx |
| 平板 | 768px - 1024px | 14px |
| 桌面 | > 1024px | 16px |

### 媒体查询

```scss
// 移动端
@media (max-width: 768px) {
  .container {
    padding: 20rpx;
  }
}

// 平板
@media (min-width: 768px) and (max-width: 1024px) {
  .container {
    padding: 40px;
  }
}

// 桌面
@media (min-width: 1024px) {
  .container {
    padding: 60px;
  }
}
```

---

## 动画效果

### 过渡动画

```scss
// 渐变过渡
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

// 滑动过渡
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}

.slide-enter-from {
  transform: translateX(-100%);
}

.slide-leave-to {
  transform: translateX(100%);
}
```

### 按钮动画

```scss
.button {
  transition: all 0.3s ease;

  &:active {
    transform: scale(0.95);
  }
}
```

---

**页面设计完成，所有样式均已在对应页面文件中实现。**
