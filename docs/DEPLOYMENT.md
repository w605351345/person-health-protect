# 部署文档

## 📋 目录

- [系统要求](#系统要求)
- [一键部署（Docker）](#一键部署docker)
- [手动部署](#手动部署)
- [配置说明](#配置说明)
- [故障排查](#故障排查)

---

## 系统要求

### 硬件要求

| 组件 | 最低配置 | 推荐配置 |
|------|---------|---------|
| CPU | 2核 | 4核+ |
| 内存 | 4GB | 8GB+ |
| 硬盘 | 20GB | 50GB+ |

### 软件要求

- Docker 20.10+
- Docker Compose 2.0+
- Git 2.0+

---

## 一键部署（Docker）

### 1. 克隆项目

```bash
git clone https://github.com/w605351345/person-health-protect.git
cd person-health-protect
```

### 2. 启动服务

```bash
docker-compose up -d
```

### 3. 查看服务状态

```bash
docker-compose ps
```

预期输出：

```
NAME                          STATUS
person-health-backend          Up (healthy)
person-health-frontend-h5     Up
person-health-frontend-pc     Up
person-health-mysql           Up (healthy)
person-health-redis           Up (healthy)
```

### 4. 查看日志

```bash
# 查看所有服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f backend
docker-compose logs -f mysql
```

### 5. 停止服务

```bash
docker-compose down
```

### 6. 清理数据

```bash
# 停止并删除所有容器、网络和卷
docker-compose down -v
```

---

## 手动部署

### 1. 环境准备

#### Java 环境

```bash
# 安装 OpenJDK 17
sudo apt update
sudo apt install -y openjdk-17-jdk

# 验证安装
java -version
```

#### MySQL 数据库

```bash
# 安装 MySQL 8.0
sudo apt install -y mysql-server

# 启动 MySQL
sudo systemctl start mysql
sudo systemctl enable mysql

# 登录 MySQL
sudo mysql -u root -p
```

创建数据库：

```sql
CREATE DATABASE person_health CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户
CREATE USER 'health_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON person_health.* TO 'health_user'@'localhost';
FLUSH PRIVILEGES;

-- 导入数据库
USE person_health;
SOURCE database/schema.sql;
SOURCE database/test-data.sql;
```

#### Redis 缓存

```bash
# 安装 Redis
sudo apt install -y redis-server

# 启动 Redis
sudo systemctl start redis
sudo systemctl enable redis

# 测试 Redis
redis-cli ping
# 应返回: PONG
```

### 2. 后端部署

```bash
cd backend

# 修改配置文件
vim src/main/resources/application.yml

# 修改数据库连接
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/person_health?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: health_user
    password: your_password

# 修改 Redis 连接
spring:
  data:
    redis:
      host: localhost
      port: 6379

# 编译项目
mvn clean package -DskipTests

# 启动服务
nohup java -jar target/person-health-protect-1.0.0.jar > backend.log 2>&1 &

# 查看日志
tail -f backend.log
```

### 3. 前端部署

#### 移动端（UniApp H5）

```bash
cd frontend/mobile

# 安装依赖
npm install

# 构建生产版本
npm run build:h5

# 部署到 Nginx
sudo cp -r dist/build/h5/* /var/www/health-mobile/
```

配置 Nginx：

```nginx
server {
    listen 80;
    server_name health.example.com;

    location / {
        root /var/www/health-mobile;
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080/api;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

#### PC 端（Vue）

```bash
cd frontend/pc

# 安装依赖
npm install

# 构建生产版本
npm run build

# 部署到 Nginx
sudo cp -r dist/* /var/www/health-pc/
```

配置 Nginx：

```nginx
server {
    listen 80;
    server_name health-pc.example.com;

    location / {
        root /var/www/health-pc;
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080/api;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

---

## 配置说明

### 后端配置文件

位置：`backend/src/main/resources/application.yml`

```yaml
# 数据库配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/person_health
    username: root
    password: your_password

# Redis 配置
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:  # 有密码时填写

# JWT 配置
jwt:
  secret: your-secret-key  # 修改为随机字符串
  expiration: 86400000     # 24小时
  refresh-expiration: 604800000  # 7天

# 文件上传配置
file:
  upload-path: /data/uploads  # 文件存储路径
  max-size: 10485760         # 10MB
  allowed-types: image/jpeg,image/png,image/jpg
```

---

## 故障排查

### 1. 数据库连接失败

**问题**：`Communications link failure`

**解决方案**：

```bash
# 检查 MySQL 是否运行
sudo systemctl status mysql

# 检查防火墙
sudo ufw allow 3306

# 检查数据库配置
vim backend/src/main/resources/application.yml
```

### 2. Redis 连接失败

**问题**：`Unable to connect to Redis`

**解决方案**：

```bash
# 检查 Redis 是否运行
sudo systemctl status redis

# 测试连接
redis-cli ping

# 检查防火墙
sudo ufw allow 6379
```

### 3. 前端无法访问后端

**问题**：跨域错误

**解决方案**：

检查 Nginx 配置，确保 `/api` 正确代理到后端。

### 4. Docker 容器启动失败

**问题**：容器反复重启

**解决方案**：

```bash
# 查看容器日志
docker-compose logs backend

# 检查服务依赖
docker-compose ps

# 重启服务
docker-compose restart backend
```

---

## 访问地址

部署成功后，可通过以下地址访问：

| 服务 | 地址 | 说明 |
|------|------|------|
| 后端 API | http://localhost:8080/api | RESTful API |
| API 文档 | http://localhost:8080/api/swagger-ui.html | Swagger UI |
| 移动端（H5） | http://localhost:3000 | UniApp H5 |
| PC 端 | http://localhost:3001 | Vue PC 端 |

---

## 测试账号

```
手机号：13800138000
密码：123456
```

---

## 生产环境建议

### 1. 使用 HTTPS

配置 SSL 证书，启用 HTTPS。

### 2. 使用反向代理

使用 Nginx 作为反向代理，提高性能和安全性。

### 3. 数据备份

定期备份数据库和文件存储。

### 4. 监控日志

使用 ELK 或其他日志监控系统。

### 5. 修改默认密码

修改数据库密码、JWT 密钥等敏感配置。

---

**部署完成后，请查看 [使用文档](USAGE.md) 了解系统使用方法。**
