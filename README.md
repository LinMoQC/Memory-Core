# Memory-Core
这里是Memory Blog的后端仓库
## 项目结构
```bash
Memory-Core
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── memory
│   │   │           └── blog
│   │   │               ├── aspect 切面
│   │   │               ├── config 配置
│   │   │               ├── controller 控制器 
│   │   │               ├── dao 数据访问层 ，和 mapper 冲突
│   │   │               ├── entity 实体类 
│   │   │               ├── enums 枚举
│   │   │               ├── exception 异常,TODO
│   │   │               ├── interceptor 拦截器
│   │   │               ├── mapper mapper接口
│   │   │               ├── service 
│   │   │               ├── utils
│   │   │               └── ServerApplication.java 启动类
│   │   └── resources
│   │       ├── com.linmoblog.server.mapper mapper文件
│   │       ├── db-init 数据库初始化文件
│   │       ├── MATE-INF 
│   │       ├── application.yml 通用配置
│   │       ├── application-dev.yml 开发环境配置
│   │       ├── application-prod.yml 生产环境配置
│   │       └── logback-spring.xml

```
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
## 增加 sys_config 表保存配置信息
在 application.yml 中的配置可以写到 sys_config 表中（除了数据库配置，一般用于存储业务配置），这样可以在数据库或者后台管理界面修改项目配置（TODO）。
config_key 对应配置的 key，config_value 对应配置的 value。
## 文件存储
文件存储目前继承了本地文件存储、阿里云 OSS 文件存储，后续会扩展更多的文件存储方式（Github、Gitee）
同时只支持一种文件存储方式，如果配置了多种文件存储方式，则会有报错提示。
### 阿里云 OSS 文件存储
在 application.yml 中修改配置
```yml
ali:
  enable: true # 是否启用阿里云 OSS 文件存储
  endpoint: xxx # OSS endpoint 
  accessKeyId: xxx # OSS accessKeyId
  accessKey: xxx # OSS accessKey
  bucketName: xxx # OSS bucketName
  uploadPath: xxx # OSS uploadPath
```
### 本地文件存储
在 application.yml 中修改配置
```yml
local:
  enable: true # 是否启用本地文件存储
  uploadDir: xxx # 本地文件存储路径, 例如：/usr/local/upload 或 upload-dir 。
```