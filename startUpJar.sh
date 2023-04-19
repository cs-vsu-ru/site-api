#!/usr/bin/env fish
git pull
./mvnw -Pprod clean verify
./restartDevopsJar.sh
