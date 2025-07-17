#!/usr/bin/env bash
VERSION=$1
NAMESPACE=$2
CONFIGPATH=$3
rm -rf /opt/jellyfish
git clone git@git.unipus.cn:ocean/jellyfish.git /opt/jellyfish
cd /opt/jellyfish
git checkout birdflock
git pull
cd /opt/jellyfish/${CONFIGPATH}/${NAMESPACE}
/var/kustomize edit set image swr.cn-north-4.myhuaweicloud.com/unipus/birdflock/model-adapter:${VERSION}
sed  -i "s/resources/bases/g" kustomization.yaml
git commit -am 'image update'
git push
rm -rf /opt/jellyfish
echo 'deploy success!'
