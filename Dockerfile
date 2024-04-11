# 使用官方 OpenJDK 镜像作为基础镜像
FROM openjdk:21


# 设置工作目录
WORKDIR /app
RUN ["/bin/bash", "-c", "ls"]
# 将本地应用打包后的 jar 文件复制到容器内的指定目录
COPY target/ /app/

RUN ["/bin/bash", "-c", "ls /app/"]
# 设置容器启动时运行 jar 包的命令
ENTRYPOINT ["java", "-jar", "/app/Memory-Core-1.0.0.jar"]

# 可以选择使用 CMD 定义默认启动参数（如果应用有需要的话）
CMD ["--spring.profiles.active=prod"]

# 如果你的应用需要暴露端口给外部访问，可以添加 EXPOSE 指令
EXPOSE 8080