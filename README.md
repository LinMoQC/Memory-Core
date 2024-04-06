# Memory-Core
这里是Memory Blog的后端仓库

## JDK版本 21.0.2

## 在 application.yml 中修改配置
![image](https://github.com/LinMoQC/Memory-Core/assets/59323207/bce5f48c-a9f8-40fb-a933-084e769e0fd9)

## 使用
**在yml文件中absolutePath配置应为：classpath:/upload/**
```bash
cd Memory-Core
mvn spring-boot:run

初始账号：
admin
123456
```
## 环境搭建
### minio搭建
推荐使用 `docker` 进行安装。
命令如下：
```shell
# 创建文件夹
mkdir /home/minio
cd /home/minio
mkdir data 
mkdir config

# 运行容器
docker run --name minio \
-p 9000:9000 \
-p 9999:9999 \
-d --restart=always \
-e "MINIO_ROOT_USER=memory" \
-e "MINIO_ROOT_PASSWORD=memory" \
-v /home/minio/data:/data \
-v /home/minio/config:/root/.minio \
minio/minio server /data \
--console-address '0.0.0.0:9999'
```