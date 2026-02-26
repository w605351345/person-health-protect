# 多阶段构建

# 构建阶段
FROM maven:3.9-openjdk-17-slim AS builder

WORKDIR /app

# 复制 Maven 配置
COPY pom.xml .
COPY src ./src

# 构建
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:17-jre-slim

WORKDIR /app

# 安装必要工具
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# 复制 JAR 包
COPY --from=builder /app/target/person-health-protect-1.0.0.jar app.jar

# 创建上传目录
RUN mkdir -p /data/uploads

# 暴露端口
EXPOSE 8080

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# JVM 参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
