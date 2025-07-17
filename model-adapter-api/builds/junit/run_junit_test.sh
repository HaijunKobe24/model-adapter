#!/usr/bin/env bash
cd model-adapter-api/builds/junit/
COMPOSE_ID=${JOB_NAME:-local}

docker network prune -f
docker-compose -p ${COMPOSE_ID} down
docker-compose -p ${COMPOSE_ID} rm -f
docker-compose -p ${COMPOSE_ID} up -d --build

sleep 5

docker-compose -p ${COMPOSE_ID} exec --privileged -T main /bin/bash  model-adapter-api/builds/junit/test.sh
docker-compose -p ${COMPOSE_ID} down