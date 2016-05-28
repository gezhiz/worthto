#!/bin/sh
# 编译脚本
#
#  mvn clean package -Dmaven.test.skip=true -Ptest
#  mvn clean package -Dmaven.test.skip=true -Pprod
# ./deploy.sh  xer

target_host=$1

scp target/xer.war root@$target_host:~/xer-deploy/
scp deploy.sh root@$target_host:~/xer-deploy/
ssh root@$target_host "chmod +x ~/xer-deploy/deploy.sh"
ssh root@$target_host "cd ~/xer-deploy; ./deploy.sh xer"