#!/usr/bin/env bash
VERSION=$1
NS=$2
echo 'build image !'
docker stop ma-service-build-${NS}
docker rm ma-service-build-${NS}
docker build -f model-adapter-api/builds/docker/MavenDockerfile -t base:build_${NS} .
docker run -dit --name="ma-service-build-${NS}" base:build_${NS} /bin/bash
# copy file
docker cp ma-service-build-${NS}:/app/model-adapter-api/target/model-adapter-api.jar  ./
#gpc

#build api image
docker build -f model-adapter-api/builds/docker/Dockerfile -t swr.cn-north-4.myhuaweicloud.com/unipus/birdflock/model-adapter:${NS}_ma-api_v${VERSION} .
docker push swr.cn-north-4.myhuaweicloud.com/unipus/birdflock/model-adapter:${NS}_ma-api_v${VERSION}
#need more settings
echo '-----------------------buildpathr--------------'
#delete
docker stop ma-service-build-${NS}
docker rm ma-service-build-${NS}
rm -f model-adapter-api.jar