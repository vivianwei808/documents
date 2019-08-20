#!/bin/bash
#by ww
#system info.
#查看网络相关信息
echo 查看网络相关信息
ip a
hostname
cat /etc/hosts
cat /etc/resolv.conf
ip r
#查看设备包括内存、cpu、磁盘等使用情况
echo 查看设备包括内存、cpu、磁盘等使用情况
cat /proc/cpuinfo
lscpu
cat /proc/meminfo |grep -i total
free 
cat /proc/partitions
df -hT
##查看进程和服务情况
echo 查看进程和服务情况
ps aux |wc -l
service --status-all
chkconfig --list |grep 3:on