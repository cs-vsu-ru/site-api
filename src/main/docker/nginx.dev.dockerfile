FROM ghcr.io/cs-vsu-ru/site-web/web:latest as web

FROM nginx:1.24.0 as nginx

WORKDIR /etc/nginx

COPY ./nginx.stage.conf.conf ./templates/nginx.conf.conf
COPY --from=web /web ./html
