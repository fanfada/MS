#FROM nexus.cmss.com:8090/paas-docker/ncndb/ncndb-be/local-openjava8-base:20230506-1
FROM openjdk:8-jdk
#FROM openjdk:8-jdk-slim
#FROM openjdk:8-jre
#FROM openjdk:8-alpine
MAINTAINER fanfada
ENV TZ="Asia/Shanghai"
ADD target/managesystem.jar /app/myapp.jar
EXPOSE 8080
# 运行jar包
ENTRYPOINT ["java", "-Xms128m","-Xmx256m","-XX:MetaspaceSize=128M","-XX:MaxMetaspaceSize=256M","-jar", "/app/myapp.jar", "--spring.profiles.active=dev"]