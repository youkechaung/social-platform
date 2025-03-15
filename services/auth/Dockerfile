# 使用多阶段构建减小最终镜像大小
FROM maven:3.8.4-openjdk-17-slim AS builder

# 设置工作目录
WORKDIR /build

# 首先复制 pom 文件
COPY ./pom.xml /build/pom.xml
COPY ./auth/pom.xml /build/auth/pom.xml
COPY ./common/pom.xml /build/common/pom.xml

# 复制源代码
COPY ./auth/src /build/auth/src
COPY ./common/src /build/common/src

# 构建应用
RUN mvn -B -f /build/pom.xml clean package -DskipTests

# 运行阶段
FROM openjdk:17-jdk-slim

WORKDIR /app

# 复制构建产物
COPY --from=builder /build/auth/target/*.jar /app/app.jar

# 设置时区
ENV TZ=Asia/Shanghai

# 设置 JVM 参数
ENV JAVA_OPTS="\
    -server \
    -Xms1g \
    -Xmx1g \
    -Xmn256m \
    -XX:MetaspaceSize=128m \
    -XX:MaxMetaspaceSize=256m \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=100 \
    -XX:+ParallelRefProcEnabled"

# 暴露端口
EXPOSE 8081

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --spring.profiles.active=prod"]
