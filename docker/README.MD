# 镜像构建
## JDK
docker build -f .\jdk_wfq -t jdk_wfq .
# 构建容器并启动
docker-compose -f .\wfq.yum up
# 端口访问
## API Document
http://localhost:48080/swagger-ui/index.html
## 数据库
localhost:43307
user:root
password:W2316195243
## Netty
localhost:48888
## kkFile
http://localhost:48012