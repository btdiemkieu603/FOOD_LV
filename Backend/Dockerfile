# Sử dụng hình ảnh OpenJDK phiên bản 11
#FROM openjdk:11-jre-slim
FROM openjdk:17

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép tệp jar vào thư mục làm việc trong container
#COPY target/*.jar app.jar
ADD target/Backend-0.0.1-SNAPSHOT.jar Backend-0.0.1-SNAPSHOT.jar

# Mở cổng 8080 ra ngoài thế giới ngoài container này
EXPOSE 8080

# Chạy tệp jar
#CMD ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-jar", "Backend-0.0.1-SNAPSHOT.jar"]


