#!/bin/bash

./mvnw clean package -DskipTests
docker build . -t vocabularybuilder
docker-compose up
