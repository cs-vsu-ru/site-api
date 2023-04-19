#!/usr/bin/env fish
set id (ps -A | grep java | awk '{print $1}')
kill $id
nohup  java -jar target/site-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod,api-docs > log.txt &


