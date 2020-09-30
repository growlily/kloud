FROM openjdk:11
WORKDIR /backend
COPY ./config ~/.kube/config
COPY ./.mvn ./.mvn
COPY ./mvnw ./pom.xml ./
RUN ./mvnw package --fail-never
COPY . .
RUN ./mvnw package -Dmaven.test.skip=true
CMD [ "java", "-jar", "./target/backend-0.0.1-SNAPSHOT.jar" ]
