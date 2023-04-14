#!/usr/bin/env fish
systemctl stop mysql
cd /$HOME/vsu/site-api
docker-compose -f src/main/docker/redis.yml down
docker-compose -f src/main/docker/mysql.yml down
docker-compose -f src/main/docker/mysql.yml up -d
docker-compose -f src/main/docker/redis.yml up -d
./mvnw
