FROM node:12.14.1 as builder
ARG ENVIRONMENT=staging
WORKDIR /source
COPY package.json yarn.lock /source/
RUN yarn install
COPY . /source
RUN yarn build

FROM nginx:1.17.7
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /source/build /var/www/html
EXPOSE 80
