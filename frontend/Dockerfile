FROM node:12 AS frontend
RUN npm i -g serve
WORKDIR /frontend
COPY ./package.json ./package-lock.json ./
RUN npm i
COPY . .
RUN npm run build
CMD [ "serve", "-s", "dist" ]
