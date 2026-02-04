# 使用轻量级OpenJDK 17运行时镜像
FROM jcloudhub.jcloudcs.com/jdcloudhub/openjdk

# 设置工作目录
WORKDIR /app

# 复制jar文件到容器中
COPY cz-blog-backend-1.0-SNAPSHOT.jar app.jar

# 暴露应用端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "app.jar"]