FROM ghcr.io/cs-vsu-ru/site-web/web:prod as web

FROM nginx:1.24.0 as nginx

WORKDIR /etc/nginx

COPY ./src/main/docker/config/nginx/nginx.prod.conf.conf ./templates/nginx.conf.conf
COPY ../../../key.pem ./certs/key.pem
COPY ../../../crt.pem ./certs/crt.pem
COPY --from=web /web ./html
