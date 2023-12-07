FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
ARG JAR_FILE=build/libs/yjp-delivery-0.0.1-SNAPSHOT.jar
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
COPY ${JAR_FILE} yjp.jar

ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENTRYPOINT ["java","-jar", "/yjp.jar"]
