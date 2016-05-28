#!/bin/sh
# 编译脚本
#
#  mvn clean package -Dmaven.test.skip=true -Ptest
#  mvn clean package -Dmaven.test.skip=true -Pprod
# ./deploy.sh  xer


mvn clean package -Dmaven.test.skip=true -Pprod
