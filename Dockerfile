FROM maven:3.8.5-openjdk-18-slim as build
COPY . /usr/GR02_P1_622_24A
WORKDIR /usr/GR02_P1_622_24A
RUN mvn clean install
RUN mvn package

FROM bitnami/tomcat:10.1.23 as serve
COPY --from=build /usr/GR02_P1_622_24A/target/hotel.war /opt/bitnami/tomcat/webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]