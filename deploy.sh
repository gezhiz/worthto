#!/bin/sh
# 简单的tomcat自动化部署脚本 
# 
# 一、实现功能：
# 1、检查tomcat进程是否存在，如果存在则kill掉
# 2、备份现有war包到tomcat/backup目录
#   tar -cf ecssportal.war ecssportal/
# 3、复制当前目录新war包到tomcat/webapps目录
# 4、启动tomcat
# 5、显示tomcat日志
# 
# 二、注意：
#  1、使用时，需要先修改backup_path,tomcat_path路径
#  2、目录下同时存在deploy.sh和server.war
# 
# 三、执行命令
#  mvn clean package -Dmaven.test.skip=true -Ptest
#  mvn clean package -Dmaven.test.skip=true -Pprod
# ./deploy.sh  xer
#
XER_TOMCAT_HOME=/usr/local/tomcat/apache-tomcat-7.0.68
if [ -z $XER_TOMCAT_HOME ]; then 
  echo -e "not found XER_TOMCAT_HOME" 
  exit -1
fi

now=`date +%Y%m%d%H%M%S`
tomcat_path=$XER_TOMCAT_HOME
backup_path=$tomcat_path"/backup"

echo -e $tomcat_path 
echo -e $backup_path 
#exit -1
# 参数校验
deploywar=$1
if [ -e "$deploywar.war" ]; then
  echo -e "\033[34m war archive: $deploywar.war \033[0m"
else 
  echo -e "\033[31m war archive '$deploywar.war' not exists \033[0m"
  exit -1
fi
#create backup dir
if [ ! -d "$backup_path" ]; then
  mkdir "$backup_path"
fi
echo "tomcat home: $tomcat_path"
echo "backup path: $backup_path"
echo 'try to stop tomcat...'
pid=`ps aux|grep "java"|grep "$tomcat_path"|awk '{printf $2}'`
if [ -n $pid ]; then
  echo "tomcat pid: $pid";
  kill -9 $pid;
fi
echo 'stop tomcat finished...'
echo 'backup old archive...' 
tar -cvf $tomcat_path/webapps/$deploywar.tar $tomcat_path/webapps/$deploywar
if [ -f "$tomcat_path/webapps/$deploywar.tar" ]; then
  mv -v "$tomcat_path/webapps/$deploywar.tar" "$backup_path/$deploywar$now.tar";  
fi
echo 'delete old archive...' 
rm -rf $tomcat_path/webapps/$deploywar 
echo 'delete old archive work cache...'
rm -rf $tomcat_path/work/Catalina/localhost/$deploywar/ 
echo "copy $deploywar.war archive to webapps.."
mkdir $tomcat_path/webapps/$deploywar
cp -v "$deploywar.war" "$tomcat_path/webapps/$deploywar"
echo 'uzip file,delete file'   
cd $tomcat_path/webapps/$deploywar/
unzip $tomcat_path/webapps/$deploywar/$deploywar.war
rm -rf $tomcat_path/webapps/$deploywar/$deploywar.war
mkdir -p $tomcat_path/webapps/ROOT
rm -rf $tomcat_path/webapps/ROOT/*
mv $tomcat_path/webapps/$deploywar/* $tomcat_path/webapps/ROOT/
cd $tomcat_path/webapps/
rm -rf $tomcat_path/webapps/$deploywar
echo -e "\033[32m"
echo 'startup tomcat...'
sh $tomcat_path/bin/startup.sh
tail -10f $tomcat_path/logs/catalina.out