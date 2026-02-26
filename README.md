# 家庭健康档案系统 (Person Health Protect)

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![Redis](https://img.shields.io/badge/Redis-7.0-red.svg)
![Vue.js](https://img.shields.io/badge/Vue.js-3.0-42b883.svg)
![UniApp](https://img.shields.io/badge/UniApp-3.0-green.svg)

## 📋 项目简介

家庭健康档案系统是一个面向中国用户的健康管理平台，整合了身份认证、健康数据管理、医疗记录查询、智能保险推荐等功能。支持多端访问（Android、iOS、PC、小程序）。

## ✨ 核心功能

### 🔐 用户管理
- 手机号注册/登录
- 身份证绑定（一手机号一身份证）
- 身份证照片上传（智能识别，仅允许身份证）
- JWT 身份验证

### 📊 健康档案
- 个人基本信息管理
- 健康指标录入（体重、血压、血糖、血脂等）
- 健康数据趋势图表
- 健康报告生成

### 🏥 医疗记录
- 医保服务对接
- 医院就医记录查询
- 既往病史管理
- 用药记录管理
- 检查报告管理

### 🛡️ 智能保险
- 符合年龄段的保险产品查询
- 基于医疗记录和病史的智能过滤
- 在投保单查询
- 保险推荐算法
- 保险购买跳转

## 🏗️ 技术架构

### 后端技术栈
- **语言**: Java 17
- **框架**: Spring Boot 3.2
- **数据库**: MySQL 8.0
- **ORM**: MyBatis 3.5
- **缓存**: Redis 7.0
- **安全**: Spring Security + JWT
- **文档**: OpenAPI 3.0 (SpringDoc)
- **构建工具**: Maven 3.9

### 前端技术栈
- **移动端**: UniApp (跨平台：Android、iOS、小程序)
- **PC端**: Vue 3 + Element Plus
- **状态管理**: Pinia
- **HTTP客户端**: Axios
- **UI组件库**:
  - 移动端: uView UI
  - PC端: Element Plus

## 📁 项目结构

```
person-health-protect/
├── backend/                      # 后端项目
│   ├── src/main/
│   │   ├── java/com/personhealth/
│   │   │   ├── controller/      # REST API 控制器
│   │   │   ├── service/         # 业务逻辑层
│   │   │   ├── mapper/          # MyBatis Mapper
│   │   │   ├── entity/          # 实体类
│   │   │   ├── dto/             # 数据传输对象
│   │   │   ├── vo/              # 视图对象
│   │   │   ├── config/          # 配置类
│   │   │   ├── security/        # 安全配置
│   │   │   ├── filter/          # 过滤器
│   │   │   ├── interceptor/     # 拦截器
│   │   │   ├── util/            # 工具类
│   │   │   └── exception/       # 异常处理
│   │   └── resources/
│   │       ├── mapper/          # MyBatis XML
│   │       ├── application.yml  # 应用配置
│   │       └── db/              # 数据库脚本
│   └── pom.xml                  # Maven 配置
│
├── frontend/                     # 前端项目
│   ├── mobile/                  # UniApp 移动端
│   │   ├── pages/               # 页面
│   │   ├── components/         # 组件
│   │   ├── api/                 # API 调用
│   │   ├── store/               # 状态管理
│   │   ├── utils/               # 工具函数
│   │   └── manifest.json        # 应用配置
│   │
│   ├── pc/                      # Vue PC 端
│   │   ├── src/
│   │   │   ├── views/           # 页面
│   │   │   ├── components/      # 组件
│   │   │   ├── api/             # API 调用
│   │   │   ├── store/           # 状态管理
│   │   │   ├── router/          # 路由配置
│   │   │   └── assets/          # 静态资源
│   │   └── package.json
│   │
│   └── shared/                  # 共享代码
│       ├── api/                 # API 定义
│       ├── types/               # TypeScript 类型
│       └── constants/           # 常量
│
├── database/                    # 数据库脚本
│   ├── schema.sql               # 建表脚本
│   └── data.sql                 # 初始化数据
│
├── docs/                        # 项目文档
│   ├── DEPLOYMENT.md            # 部署指南
│   ├── USAGE.md                 # 使用文档
│   ├── TESTING.md               # 测试流程文档
│   └── PAGES.md                 # 页面设计文档
│
├── QUICKSTART.md                # 快速启动指南
├── CHANGELOG.md                 # 版本更新日志
└── README.md                    # 本文件
```

## 🚀 快速开始

### 环境要求
- JDK 17+
- Maven 3.9+
- MySQL 8.0+
- Redis 7.0+
- Node.js 18+
- HBuilderX 3.9+ (UniApp 开发)

### 后端启动
```bash
cd backend

# 导入数据库
mysql -u root -p < ../database/schema.sql

# 修改配置文件
vim src/main/resources/application.yml
# 配置数据库连接、Redis连接等

# 编译项目
mvn clean package

# 启动服务
mvn spring-boot:run

# 访问 API 文档
open http://localhost:8080/swagger-ui.html
```

### 前端启动

#### 移动端 (UniApp)
```bash
cd frontend/mobile

# 安装依赖
npm install

# 运行到浏览器
npm run dev:h5

# 运行到微信小程序
npm run dev:mp-weixin

# 构建 Android
npm run build:app-android

# 构建 iOS
npm run build:app-ios
```

#### PC 端 (Vue)
```bash
cd frontend/pc

# 安装依赖
npm install

# 开发模式
npm run dev

# 生产构建
npm run build

# 预览
npm run preview
```

## 📋 API 接口

### 用户认证
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户登出
- `POST /api/auth/refresh-token` - 刷新Token

### 用户管理
- `GET /api/user/profile` - 获取用户信息
- `PUT /api/user/profile` - 更新用户信息
- `POST /api/user/bind-idcard` - 绑定身份证
- `POST /api/user/upload-idcard` - 上传身份证照片

### 健康数据
- `POST /api/health/record` - 录入健康指标
- `GET /api/health/records` - 获取健康记录
- `GET /api/health/trends` - 健康趋势分析
- `GET /api/health/report` - 生成健康报告

### 医疗记录
- `GET /api/medical/visits` - 就医记录
- `GET /api/medical/visits/insurance` - 从医保服务查询
- `GET /api/medical/visits/hospital` - 从医院系统查询
- `GET /api/medical/history` - 既往病史
- `GET /api/medical/medications` - 用药记录
- `POST /api/medical/sync` - 同步医疗记录

### 保险服务
- `GET /api/insurance/products` - 保险产品列表
- `GET /api/insurance/recommend` - 智能推荐
- `GET /api/insurance/filter` - 过滤保险产品
- `GET /api/insurance/my-policies` - 我的保单
- `GET /api/insurance/detail/{id}` - 保险详情
- `GET /api/insurance/purchase/{id}` - 跳转购买

**API 文档**：启动后访问 http://localhost:8080/api/swagger-ui.html 查看 Swagger UI

## 🗄️ 数据库设计

### 核心表结构
- `user` - 用户表
- `user_profile` - 用户档案
- `health_record` - 健康记录
- `medical_visit` - 就医记录
- `medical_history` - 既往病史
- `medication` - 用药记录
- `insurance_product` - 保险产品
- `user_policy` - 用户保单

## 🔐 安全特性

### 数据安全
- 密码 BCrypt 加密
- 敏感信息 AES 加密存储
- 身份证号脱敏显示
- SQL 注入防护

### 接口安全
- JWT Token 认证
- HTTPS 传输加密
- 请求签名验证
- 访问频率限制

### 文件安全
- 文件类型白名单验证
- 文件内容深度检测（AI 识别身份证）
- 病毒扫描
- 存储隔离

## 📱 多端适配

### 移动端 (UniApp)
- 微信小程序
- 支付宝小程序
- H5 网页
- Android App
- iOS App

### PC 端 (Vue)
- 响应式设计
- 支持主流浏览器
- 键盘快捷键

## 🧪 测试

### 单元测试
```bash
cd backend
mvn test
```

### 接口测试
```bash
# 使用 Postman 导入测试集合
# 文件位置: docs/postman_collection.json
```

## 📈 性能优化

### 后端优化
- Redis 缓存热点数据
- 数据库索引优化
- 慢查询优化
- 异步处理（线程池）

### 前端优化
- 图片懒加载
- 路由懒加载
- 虚拟滚动
- 本地缓存

## 🚢 部署指南

### Docker 部署
```bash
# 构建镜像
docker-compose build

# 启动服务
docker-compose up -d

# 查看日志
docker-compose logs -f
```

### 传统部署
详见：[DEPLOYMENT.md](docs/DEPLOYMENT.md)

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 👥 联系方式

- 项目地址：https://github.com/w605351345/person-health-protect
- 问题反馈：https://github.com/w605351345/person-health-protect/issues

---

**让健康管理更智能，让生活更健康！** 💪
