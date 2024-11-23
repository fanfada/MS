FROM nexus.cmss.com:8090/paas-docker/ncndb/ncndb-be/local-openjava8-base:20230506-1
ENV TZ="Asia/Shanghai"
ADD target/managesystem.jar /app/myapp.jar
EXPOSE 8090
# 运行jar包
ENTRYPOINT ["java", "-Xms512m","-Xmx512m","-XX:PermSize=256M","-XX:MaxPermSize=256M","-jar", "/app/myapp.jar"]