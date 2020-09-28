FROM node:12 AS frontend
WORKDIR /frontend
COPY ./frontend/package.json ./frontend/package-lock.json ./
RUN npm install
COPY ./frontend .
RUN NODE_ENV=production SERVER=10.251.253.65:8888 npm run build

FROM openjdk:11
WORKDIR /backend
COPY ./backend/.mvn ./.mvn
COPY ./backend/mvnw ./backend/pom.xml ./
RUN ./mvnw package --fail-never
COPY ./backend .
COPY --from=frontend /frontend/dist ./src/main/resources/static
RUN ./mvnw package -Dmaven.test.skip=true
