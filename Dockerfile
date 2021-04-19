FROM openjdk:8

ENV SPRING_DATASOURCE_URL=jdbc:mysql://fedex-db/fedex?serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_SECRET_KEY=asd123

WORKDIR /app
COPY ../backend/fedex-0.0.1-SNAPSHOT.jar /app/app.jar

# Ez akkor kell ha a futtató környezet nem NAT-olja
# szól a futtatónak, hogy a 8080-as porton lesz elérhető
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]