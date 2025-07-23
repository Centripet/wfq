#!/bin/bash

# 启动 JobManager（后台服务）
/docker-entrypoint.sh jobmanager &

# 等待 JobManager 启动（确保 Web UI 可用）
sleep 10

# 提交 Job
flink run /FlinkJob-1.0-SNAPSHOT.jar

# 阻止容器退出
tail -f /dev/null