# 快速启动指南

## 🚀 5分钟快速启动

### 方式一：Docker 一键启动（推荐）

```bash
# 1. 克隆项目
git clone https://github.com/w605351345/person-health-protect.git
cd person-health-protect

# 2. 启动所有服务
docker-compose up -d

# 3. 等待启动完成（约2-3分钟）
docker-compose logs -f

# 4. 访问系统
# 移动端 H5：http://localhost:3000
# PC 端：http://localhost:3001
# API 文档：http://localhost:8080/api/swagger-ui.html

# 5. 登录测试
# 手机号：13800138000
# 密码：123456
```

### 方式二：本地开发启动

#### 1. 启动数据库和缓存

```bash
# 启动 MySQL
docker run --name mysql-health -e MYSQL_ROOT_PASSWORD=root123456 -e MYSQL_DATABASE=person_health -p 3306:3306 -d mysql:8.0

# 启动 Redis
docker run --name redis-health -p 6379:6379 -d redis:7.0-alpine

# 导入数据库
docker exec -i mysql-health mysql -uroot -proot123456 person_health < database/schema.sql
docker exec -i mysql-health mysql -uroot -proot123456 person_health < database/test-data.sql
```

#### 2. 启动后端

```bash
cd backend

# 修改配置文件（根据需要）
vim src/main/resources/application.yml

# 启动服务
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/person-health-protect-1.0.0.jar
```

#### 3. 启动前端（H5）

```bash
cd frontend/mobile

# 安装依赖
npm install

# 启动开发服务器
npm run dev:h5

# 访问：http://localhost:3000
```

#### 4. 启动前端（PC）

```bash
cd frontend/pc

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 访问：http://localhost:3001
```

---

## 📱 访问地址

| 服务 | 地址 | 说明 |
|------|------|------|
| 移动端 H5 | http://localhost:3000 | UniApp H5 |
| PC 端 | http://localhost:3001 | Vue PC 端 |
| 后端 API | http://localhost:8080/api | RESTful API |
| API 文档 | http://localhost:8080/api/swagger-ui.html | Swagger UI |

---

## 🔐 测试账号

```
手机号：13800138000
密码：123456
```

**测试用户：张三**
- 姓名：张三
- 身份证：110101199001011234
- 年龄：34岁
- 性别：男
- 地址：北京市朝阳区建国路88号

---

## 🎯 快速测试流程

### 1. 登录系统

1. 打开 http://localhost:3000
2. 输入手机号：13800138000
3. 输入密码：123456
4. 点击"登录"

### 2. 查看个人档案

1. 点击底部"我的"标签
2. 进入"个人档案"
3. 查看张三的档案信息

### 3. 录入健康数据

1. 点击底部"健康"标签
2. 点击右下角"+ 录入数据"
3. 填写健康指标并保存

### 4. 查看医疗记录

1. 点击底部"医疗"标签
2. 查看张三的就医记录
3. 切换"医保"或"医院"查看不同来源

### 5. 查看保险服务

1. 点击底部"保险"标签
2. 查看智能推荐保险
3. 查看我的保单

---

## 🔧 常见问题

### Q1: Docker 启动失败？

**A**: 检查端口占用

```bash
# 查看端口占用
lsof -i :8080
lsof -i :3000
lsof -i :3001
lsof -i :3306
lsof -i :6379

# 停止占用端口的进程
kill -9 <PID>
```

### Q2: 数据库连接失败？

**A**: 检查 MySQL 是否启动

```bash
docker-compose logs mysql

# 进入数据库测试
docker exec -it person-health-mysql mysql -uroot -proot123456
```

### Q3: 前端无法访问后端？

**A**: 检查后端是否启动

```bash
# 查看后端日志
docker-compose logs backend

# 测试 API
curl http://localhost:8080/api/auth/login
```

### Q4: 测试账号登录失败？

**A**: 确认测试数据已导入

```bash
# 查看数据库
docker exec -it person-health-mysql mysql -uroot -proot123456 person_health

# 执行查询
SELECT * FROM user WHERE phone = '13800138000';
```

---

## 📚 更多文档

- [部署文档](DEPLOYMENT.md) - 详细部署指南
- [使用文档](USAGE.md) - 用户使用指南
- [测试流程](TESTING.md) - 功能测试文档
- [页面设计](PAGES.md) - 页面设计规范

---

## 🆘 获取帮助

如遇问题，请：

1. 查看 [GitHub Issues](https://github.com/w605351345/person-health-protect/issues)
2. 联系技术支持：support@personhealth.com
3. 查看日志：`docker-compose logs -f`

---

**快速启动完成！祝您使用愉快！** 🎉
