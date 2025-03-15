#!/bin/bash

# 阿里云容器镜像服务配置
REGISTRY="registry.cn-hangzhou.aliyuncs.com"
NAMESPACE="social-platform"
VERSION=$(date +%Y%m%d%H%M)

# 服务列表
SERVICES=("gateway" "auth" "user" "message" "notification" "search")

# 登录阿里云容器镜像服务
echo "登录阿里云容器镜像服务..."
docker login --username=${DOCKER_USERNAME} --password=${DOCKER_PASSWORD} ${REGISTRY}

# 构建并推送每个服务的镜像
for SERVICE in "${SERVICES[@]}"
do
    echo "开始构建 ${SERVICE} 服务..."
    
    # 构建镜像
    docker build -t ${REGISTRY}/${NAMESPACE}/${SERVICE}-service:${VERSION} \
                -t ${REGISTRY}/${NAMESPACE}/${SERVICE}-service:latest \
                -f services/${SERVICE}/Dockerfile .
    
    # 推送镜像
    echo "推送 ${SERVICE} 镜像到阿里云..."
    docker push ${REGISTRY}/${NAMESPACE}/${SERVICE}-service:${VERSION}
    docker push ${REGISTRY}/${NAMESPACE}/${SERVICE}-service:latest
    
    echo "${SERVICE} 服务镜像构建完成"
done

echo "所有服务镜像构建完成"
