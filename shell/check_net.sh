#!/bin/bash
read  -p "please input your net want to check (for example:192.168.100):" ip
for i in {1..254};do                    ##执行for循环语句，取值范围为1-254
  ping -c 2 -I eth0 $ip$i &>/dev/null     ##编写循环体，使用ping命令测试网络主机连通性
  if [ $? -eq 0 ];then                    ##使用if判断语句，判断ping命令的结果，若上条命令执行成功
    B=$(arping -I eth0 -f $ip$i |grep Unicast |awk '{print $5}')    ##若上条ping命令执行成功，证明此主机存在，定义变量B为此主机的MAC地址
	echo "$ip$i is starting MAC=$B"         ##输入结果和主机的MAC地址变量B到屏幕
  else                                    ##若ping命令执行不成功，证明主机不存在
	echo "$ip$i is stoping"
  fi
done